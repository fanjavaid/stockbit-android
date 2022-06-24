package com.stockbit.hiring.feature_login

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stockbit.hiring.feature_login.ui.LoginFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    private lateinit var scenario: FragmentScenario<LoginFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun verifyLoginComponentsVisibility() {
        verifyComponentVisibility()
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