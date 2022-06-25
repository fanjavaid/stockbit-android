package com.stockbit.hiring.feature_login.ui

import android.os.Bundle
import androidx.navigation.NavDirections
import com.stockbit.common.base.BaseViewModel
import com.stockbit.common.utils.Event
import com.stockbit.hiring.feature_login.R
import com.stockbit.model.User
import com.stockbit.repository.UserRepository

class LoginViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    fun login(user: User) {
        val validateResult = validate(user)
        if (validateResult != null) {
            _snackbarError.value = Event(validateResult)
            return
        }
        navigate(getHomeNavDirections())
    }

    private fun getHomeNavDirections(): NavDirections = object : NavDirections {
        override fun getActionId(): Int {
            return R.id.action_loginFragment_to_homeFragment
        }
        override fun getArguments(): Bundle = Bundle()
    }

    private fun validate(user: User): Int? {
        val errorMessage = R.string.invalid_username_or_password
        if (user.emailAddress.isBlank() || user.password.isBlank()) {
            return errorMessage
        }
        return if (userRepository.login(user)) null else errorMessage
    }
}
