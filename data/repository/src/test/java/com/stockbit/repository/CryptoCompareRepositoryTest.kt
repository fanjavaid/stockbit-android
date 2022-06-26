package com.stockbit.repository

import com.stockbit.common_test.rules.CoroutinesTestRule
import com.stockbit.model.CoinInfo
import com.stockbit.model.CryptoCompare
import com.stockbit.model.Data
import com.stockbit.model.Display
import com.stockbit.model.UsdCurrency
import com.stockbit.remote.CryptoCompareDataSource
import com.stockbit.repository.utils.FakeCryptoCompareLocalDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CryptoCompareRepositoryTest {

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    private val fakeLocalDataSource = FakeCryptoCompareLocalDataSource()
    private val fakeRemoteDataSource: CryptoCompareDataSource = mockk()

    private val repository: CryptoCompareRepository = CryptoCompareRepositoryImpl(
        fakeRemoteDataSource,
        fakeLocalDataSource,
        AppDispatchers(coroutineRule.testDispatcher, coroutineRule.testDispatcher)
    )

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
        fakeLocalDataSource.clear()
    }

    @Test
    fun `When success getting data from network, Then save to local DB`() {
        coroutineRule.testDispatcher.runBlockingTest {
            // Given
            val mockNetworkData: Response<CryptoCompare> = Response.success(getMockData())
            coEvery {
                fakeRemoteDataSource.fetchTotalTopTierVolume(any(), any(), any())
            } returns mockNetworkData

            // When
            repository.fetchTotalTopTierVolume(5, 0, "USD").count()

            // Then
            val cachedData = fakeLocalDataSource.getAll()
            assertThat(cachedData.size, equalTo(5))
        }
    }

    private fun getMockData(): CryptoCompare {
        return CryptoCompare().apply {
            data = List(5) {
                Data(
                    coinInfo = CoinInfo(
                        id = (it + 1).toString(),
                        name = "STOCK $it",
                        fullName = "Stock Fullname $it"
                    ),
                    display = Display(
                        usdCurrency = UsdCurrency(
                            price = "$ ${1000 * it}",
                            changeHour = "0.0",
                            changePercentageHour = "0.0"
                        )
                    )
                )
            }
        }
    }
}
