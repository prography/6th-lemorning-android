package org.prography.lemorning.src.models

import com.google.gson.annotations.SerializedName

data class Song (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val title : String?,
    @SerializedName("category_name") val category : String?,
    @SerializedName("image") val thumbnail : String?,
    @SerializedName("tags") val tags: List<String>
)

data class SongDetail (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val title: String,
    @SerializedName("category_name") val category: String?,
    @SerializedName("image") val imgUrl: String?,
    @SerializedName("alarm") val musicUrl: String?,
    @SerializedName("tags") val tags: List<String>
)

class SongCategory (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name : String,
    @SerializedName("thumbnail") val imgUrl : String?
)