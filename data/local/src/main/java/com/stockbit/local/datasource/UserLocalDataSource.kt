package com.stockbit.local.datasource

class UserLocalDataSource {

    fun getUser(username: String, password: String): Boolean {
        if (username == "dummy" && password == "123") return true
        return false
    }
}
