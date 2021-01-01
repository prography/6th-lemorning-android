package org.prography.lemorning.src.models

import com.google.gson.annotations.SerializedName

data class Card (
    @SerializedName("bank") val bank : String,
    @SerializedName("no") val no : String
)

data class CardRegisterParam(
    @SerializedName("cardNickName") val cardNickName: String,
    @SerializedName("cardNum") val cardNum: String,
    @SerializedName("expireYear") val expireYear: Int,
    @SerializedName("expireMonth") val expireMonth: Int,
    @SerializedName("birth") val birth: String,
    @SerializedName("simplePassword") val pwd2digit: String
)