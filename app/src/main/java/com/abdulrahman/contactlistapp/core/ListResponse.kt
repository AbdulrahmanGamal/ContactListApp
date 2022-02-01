package com.abdulrahman.contactlistapp.core

import com.google.gson.annotations.SerializedName

class ListResponse<T>(
    @SerializedName("results")
    val results: List<T>,
    @SerializedName("info")
    val info:PageInfo,

)
class PageInfo(
    @SerializedName("results")
    val results:Int,
    @SerializedName("page")
    val page:Int,
)