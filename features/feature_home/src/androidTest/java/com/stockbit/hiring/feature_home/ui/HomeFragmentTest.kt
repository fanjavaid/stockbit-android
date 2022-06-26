package com.stockbit.hiring.feature_home.ui

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stockbit.common_test.espresso.RecyclerViewItemCountAssertion.Companion.withItemCount
import com.stockbit.hiring.feature_home.FakeCryptoCompareRepository
import com.stockbit.hiring.feature_home.R
import com.stockbit.hiring.feature_home.testAppComponent
import com.stockbit.repository.CryptoCompareRepository
import com.stockbit.repository.utils.Resource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    private lateinit var scenario: FragmentScenario<HomeFragment>

    private val fakeRepository = FakeCryptoCompareRepository()
    private val mockRepositoryModule = module {
        factory<CryptoCompareRepository> { fakeRepository }
    }

    @Before
    fun setUp() {
        startKoin {
            modules(testAppComponent.plus(mockRepositoryModule))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun launchFragment_verifyComponentVisibility() {
        // Given
        fakeRepository.data = Resource.success(fakeRepository.getMockData())

        // When
        launchFragment()

        // Then
        val allWatchlistMenuMatcher = withText("All Watchlist")
        val addSymbolMenuMatcher = withText("Add Symbol")
        val stockListMatcher = withId(R.id.stock_list)

        onView(allWatchlistMenuMatcher).check(matches(isDisplayed()))
        onView(addSymbolMenuMatcher).check(matches(isDisplayed()))
        onView(stockListMatcher).check(withItemCount(FakeCryptoCompareRepository.MAX_ITEM))
    }

    @Test
    fun launchFragment_emptyData_verifyComponentVisibility() {
        // Given
        fakeRepository.data = Resource.success(null)

        // When
        launchFragment()

        // Then
        val emptyTitleMatcher = withText("No Data")
        val emptyDescMatcher = withText("No data to be displayed")
        val stockListMatcher = withId(R.id.stock_list)

        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.STARTED)

        onView(emptyTitleMatcher).check(matches(isDisplayed()))
        onView(emptyDescMatcher).check(matches(isDisplayed()))
        onView(stockListMatcher).check(withItemCount(0))
    }

    @Test
    fun launchFragment_withError_verifyComponentVisibility() {
        // Given
        fakeRepository.data = Resource.error(Exception(), null)

        // When
        launchFragment()

        // Then
        val errorTitleMatcher = withText("Whoops!")
        val errorDescMatcher = withText(
            "Something went wrong! Please check your internet connection."
        )
        val stockListMatcher = withId(R.id.stock_list)

        onView(errorTitleMatcher).check(matches(isDisplayed()))
        onView(errorDescMatcher).check(matches(isDisplayed()))
        onView(stockListMatcher).check(withItemCount(0))
    }

    @Test
    fun launchFragment_onLoading_verifyComponentVisibility() {
        // Given
        fakeRepository.data = Resource.loading(null)

        // When
        launchFragment()

        // Then
        val stockListMatcher = withId(R.id.stock_list)
        val loadingMatcher = withId(R.id.loading)

        onView(loadingMatcher).check(matches(isDisplayed()))
        onView(stockListMatcher).check(withItemCount(0))
    }

    @Test
    fun launchFragment_swipeToRefresh_verifyComponentVisibility() {
        // Given
        fakeRepository.data = Resource.success(null)

        // When
        launchFragment()

        // Then
        val stockListMatcher = withId(R.id.stock_list)
        onView(stockListMatcher).check(withItemCount(0))

        // When, swipe to refresh
        fakeRepository.data = Resource.success(fakeRepository.getMockData())
        onView(withId(R.id.swipe_refresh_layout)).perform(ViewActions.swipeDown())

        // Then, verify the data
        onView(stockListMatcher).check(withItemCount(FakeCryptoCompareRepository.MAX_ITEM))
    }

    private fun launchFragment() {
        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.STARTED)
    }
}
