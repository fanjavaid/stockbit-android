package com.stockbit.hiring.feature_login

import android.util.Log
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stockbit.hiring.feature_login.ui.LoginFragment
import com.stockbit.hiring.feature_login.ui.LoginViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    private lateinit var scenario: FragmentScenario<LoginFragment>

    private val mockLoginToHomeNavDirections = mockk<NavDirections>()
    private val testLoginModule = module {
        factory { mockLoginToHomeNavDirections }
        viewModel { LoginViewModel(get(), get()) }
    }

    @Before
    fun setup() {
        startKoin {
            modules(testAppComponent.plus(testLoginModule))
        }

        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun launchFragment_verifyLoginComponentsVisibility() {
        verifyComponentVisibility()
        verifyLoginWithEmptyField()
    }

    @Test
    fun launchFragment_doLogin_verifyNavigateToHome() {
        // Given
        val mockNavController = mockk<NavController>()
        every {
            mockNavController.navigate(any(), any<Navigator.Extras>())
        } returns Unit

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        Log.d("HASHCODE", "Test = ${mockNavController.hashCode()}")
        println("Test = ${mockNavController.hashCode()}")

        // When
        val usernameEmailFieldMatcher = withId(R.id.username_email_edit_text)
        val passwordFieldMatcher = withId(R.id.password_edit_text)
        val loginButtonMatcher = withText("Login")
        onView(usernameEmailFieldMatcher).perform(
            replaceText("dummy"),
            closeSoftKeyboard()
        )
        onView(passwordFieldMatcher).perform(
            replaceText("123"),
            closeSoftKeyboard()
        )
        onView(loginButtonMatcher).perform(click())

        // Then
        verify {
            mockNavController.navigate(mockLoginToHomeNavDirections, any<Navigator.Extras>())
        }
    }

    private fun verifyLoginWithEmptyField() {
        // All fields are empty
        performLogin()
        verifyErrorMessage()

        val usernameEmailFieldMatcher = withId(R.id.username_email_edit_text)
        val passwordFieldMatcher = withId(R.id.password_edit_text)

        // Password is Empty
        onView(usernameEmailFieldMatcher).perform(
            replaceText("abc"),
            closeSoftKeyboard()
        )
        performLogin()
        verifyErrorMessage()

        // Username/Email is Empty
        onView(usernameEmailFieldMatcher).perform(
            replaceText(""),
            closeSoftKeyboard()
        )
        onView(passwordFieldMatcher).perform(
            replaceText("abc"),
            closeSoftKeyboard()
        )
        performLogin()
        verifyErrorMessage()
    }

    private fun performLogin() {
        val loginButtonMatcher = withText("Login")
        onView(loginButtonMatcher).perform(click())
    }

    private fun verifyErrorMessage() {
        val errorMessageMatcher = withText("Silahkan cek kembali username dan password Anda")
        onView(errorMessageMatcher).check(matches(isDisplayed()))
    }

    private fun verifyComponentVisibility() {
        val googleLoginMatcher = withText("Masuk dengan Google")
        val facebookLoginMatcher = withText("Masuk dengan Facebook")
        val separatorMatcher = withText("Atau")

        val usernameEmailFieldMatcher = withHint("Username or Email")
        val passwordFieldMatcher = withHint("Password")

        val forgotPasswordMatcher = withText("Lupa Password")
        val loginButtonMatcher = withText("Login")
        val fingerprintButtonMatcher = withText("Masuk dengan Fingerprint")
        val registerMatcher = withText("Belum punya akun? ")
        val registerActionMatcher = withText("Daftar sekarang")

        onView(googleLoginMatcher).check(matches(isDisplayed()))
        onView(facebookLoginMatcher).check(matches(isDisplayed()))
        onView(separatorMatcher).check(matches(isDisplayed()))

        onView(usernameEmailFieldMatcher).check(matches(isDisplayed()))
        onView(passwordFieldMatcher).check(matches(isDisplayed()))

        onView(forgotPasswordMatcher).check(matches(isDisplayed()))
        onView(loginButtonMatcher).check(matches(isDisplayed()))
        onView(fingerprintButtonMatcher).check(matches(isDisplayed()))
        onView(registerMatcher).perform(scrollTo()).check(matches(isDisplayed()))
        onView(registerActionMatcher).perform(scrollTo()).check(matches(isDisplayed()))
    }
}
