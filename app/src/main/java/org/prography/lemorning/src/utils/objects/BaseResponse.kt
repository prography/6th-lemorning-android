package org.prography.lemorning.src.utils.objects

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
  @SerializedName("code")
  val code: Int,
  @SerializedName("response")
  val response: String,
  @SerializedName("message")
  val message: String,
  @SerializedName("data")
  val data: T
)

class ApiError(res: BaseResponse<*>) : Error("[${res.code}] ${res.message}")