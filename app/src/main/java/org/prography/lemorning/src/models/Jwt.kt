package org.prography.lemorning.src.models

import com.google.gson.annotations.SerializedName

data class Jwt(
    @SerializedName("key") var token : String?
)