package com.ajmir.data.retrofit.com.ajmir.retrofit.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("Data") val data: T
)
