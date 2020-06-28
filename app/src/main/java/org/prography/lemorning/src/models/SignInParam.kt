package org.prography.lemorning.src.models

import com.google.gson.annotations.SerializedName

data class SignInParam(
    @SerializedName("id") var id : String?,
    @SerializedName("password") var password : String?
)
