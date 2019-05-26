package com.asj.weather.network

import com.asj.weather.util.Constants

class ApiResponse<T> private constructor(public var status: String, public var data: T? = null) {
    var mStatus: String = status

    companion object {
        fun <T> success(response : T?): ApiResponse<T> {
            return ApiResponse(Constants.SUCCESS, response)
        }

        fun <T> failure(): ApiResponse<T> {
            return ApiResponse(Constants.FAILURE, null)
        }
    }
}