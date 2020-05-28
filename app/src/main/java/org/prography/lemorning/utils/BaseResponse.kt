package org.prography.lemorning.utils

import com.google.gson.annotations.SerializedName

abstract class BaseResponse<D : Any> (
    @SerializedName("result") val result: D
)