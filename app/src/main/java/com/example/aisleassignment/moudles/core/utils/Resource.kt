package com.example.aisleassignment.moudles.core.utils

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        FAILURE,
        EMPTY
    }

    companion object {

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> failed(message: String, data: T? = null): Resource<T> {
            return Resource(Status.FAILURE, data, message)
        }

        fun <T> empty(message: String, data: T? = null): Resource<T> {
            return Resource(Status.EMPTY, data, message)
        }

    }


}