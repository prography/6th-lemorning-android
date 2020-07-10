package org.prography.lemorning.src.models

import com.google.gson.annotations.SerializedName

data class SignInParam(
    @SerializedName("email") var email : String?,
    @SerializedName("password") var password : String?
)
