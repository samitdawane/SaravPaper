package com.samit.saravpaper.utility;


sealed class State<T> {
    class Loading<T> : State<T>()
    data class Success<T>(val data: T) : State<T>()
    data class SuccessMsg<T>(val message: String) : State<T>()
    data class Failed<T>(val message: String) : State<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T): State<T> = Success(data)
        fun <T> successMessage(msg: String) = SuccessMsg<T> (msg)
        fun <T> failed(message: String) = Failed<T>(message)

    }
}



