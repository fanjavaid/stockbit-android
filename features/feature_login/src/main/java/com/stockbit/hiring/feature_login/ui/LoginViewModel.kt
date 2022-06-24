package com.stockbit.hiring.feature_login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

        // TODO: Go to homescreen
    }

    private fun validate(user: User): Int? {
        val errorMessage = R.string.invalid_username_or_password
        if (user.emailAddress.isBlank() || user.password.isBlank()) {
            return errorMessage
        }
        return if (userRepository.login(user)) null else errorMessage
    }

    class Factory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(userRepository) as T
            }
            throw ClassCastException("Error cast ${modelClass.simpleName}")
        }
    }
}
