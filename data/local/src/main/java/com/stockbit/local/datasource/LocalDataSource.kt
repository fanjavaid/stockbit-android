package com.stockbit.local.datasource

interface LocalDataSource<T> {

    fun insert(data: T)

    fun insertAll(data: List<T>)

    fun getAll(): List<T>
}
