package com.ishdemon.camerascannertest.common

sealed class DataState<out T> {

    object Empty : DataState<Nothing>()

    object Loading : DataState<Nothing>()

    data class Success<out T>(
        val data: T
    ) : DataState<T>()

    data class Error(
        val error: Throwable
    ) : DataState<Nothing>()

}