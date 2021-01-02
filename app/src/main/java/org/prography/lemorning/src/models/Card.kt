package org.prography.lemorning.src.models

import com.google.gson.annotations.SerializedName

data class Card(
  @SerializedName("id") val id: Int,
  @SerializedName("creditCardBank") val bank: String,
  @SerializedName("cardNickname") val nickname: String,
  @SerializedName("cardNum") val cardNum: String,
  @SerializedName("expireYear") val expireYear: Int,
  @SerializedName("expireMonth") val expireMonth: Int,
  @SerializedName("birth") val birth: String, // 1997-05-15
  @SerializedName("simplePassword") val pwd2digit: String,
)

data class CardRegisterParam(
  @SerializedName("cardNickName") val cardNickName: String,
  @SerializedName("cardNum") val cardNum: String,
  @SerializedName("expireYear") val expireYear: Int,
  @SerializedName("expireMonth") val expireMonth: Int,
  @SerializedName("birth") val birth: String,
  @SerializedName("simplePassword") val pwd2digit: String
)