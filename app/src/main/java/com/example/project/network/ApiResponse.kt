package com.example.project.network

import com.google.gson.annotations.SerializedName

open class ApiResponse {
    @SerializedName("ResponseBody")
    var responseBody: Any? = null

    @SerializedName("Header")
    var header: ResponseHeader? = null
}

data class ResponseHeader(
    @SerializedName("ResponseStatus")
    val responseStatus: Boolean? = null,
    @SerializedName("ResponseCode")
    val responseCode: String? = null,
    @SerializedName("ResponseMessage")
    val responseMessage: String? = null
)