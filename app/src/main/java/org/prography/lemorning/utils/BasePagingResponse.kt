package org.prography.lemorning.utils

import com.google.gson.annotations.SerializedName

open class BasePagingResponse<D : Any> (
    @SerializedName("count") var count: Int,
    @SerializedName("results") var result: ArrayList<D>
)