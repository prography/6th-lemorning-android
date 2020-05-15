package org.prography.lemorning.utils

import com.google.gson.annotations.SerializedName

abstract class BaseResponse (
    @SerializedName("result") val result: Result
)

abstract class Result (
    @SerializedName("id") val id : Int?
)