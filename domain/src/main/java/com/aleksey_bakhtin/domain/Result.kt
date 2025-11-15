package com.aleksey_bakhtin.domain

sealed class Result{

    class Success<T>(val data: T): Result()

    class Error(val exception: Exception): Result()

    object Loading: Result()
}

fun Result.asSuccess(): Result.Success<*>? {
    return if (this is Result.Success<*>) Result.Success(data = this.data)
    else null
}