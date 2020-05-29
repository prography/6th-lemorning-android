package org.prography.lemorning.utils

import com.google.gson.annotations.SerializedName

abstract class BaseArrayResponse<D : Any> (
    @SerializedName("results") val result: ArrayList<D>
)