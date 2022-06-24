package com.stockbit.repository

import com.stockbit.local.datasource.UserLocalDataSource
import com.stockbit.model.User

interface UserRepository {
    fun login(user: User): Boolean
}

class InMemoryUserRepository(private val userDataSource: UserLocalDataSource) : UserRepository {
    override fun login(user: User): Boolean {
        return userDataSource.getUser(user.emailAddress, user.password)
    }
}
