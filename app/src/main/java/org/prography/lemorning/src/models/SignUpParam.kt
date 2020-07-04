package org.prography.lemorning.src.models

import com.google.gson.annotations.SerializedName

data class SignUpParam(
    @SerializedName("username") var username : String?,
    @SerializedName("email") var email : String?,
    @SerializedName("password1") var password : String?,
    @SerializedName("password2") var passwordRe : String?,
    @SerializedName("account") var account: Account?
)

data class Account(
    @SerializedName("profile") var profileImg : String?,
    @SerializedName("nickname") var nickname: String?,
    @SerializedName("sex") var gender : String?,// "male","female"
    @SerializedName("birth") var passwordRe : String? // "2020-02-02"
)
