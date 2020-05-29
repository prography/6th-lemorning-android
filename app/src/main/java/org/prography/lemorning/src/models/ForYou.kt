package org.prography.lemorning.src.models

import com.google.gson.annotations.SerializedName

data class ForYou (
    @SerializedName("no") var no: Int,
    @SerializedName("thumbnail") var thumbnail : String?,
    @SerializedName("title") var title : String?,
    @SerializedName("category") var category : Int
)