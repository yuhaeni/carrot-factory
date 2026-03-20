package com.haeni.carrot.core.order.shared.dto

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null,
    val errorCode: String? = null,
) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> = ApiResponse(success = true, data = data)

        fun <T> success(
            data: T,
            message: String,
        ): ApiResponse<T> = ApiResponse(success = true, data = data, message = message)

        fun <T> failure(
            message: String,
            errorCode: String? = null,
        ): ApiResponse<T> =
            ApiResponse(success = false, message = message, errorCode = errorCode)
    }
}
