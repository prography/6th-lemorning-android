package org.prography.lemorning.src.utils

import org.prography.lemorning.src.utils.objects.BaseResponse
import java.text.SimpleDateFormat
import java.util.*

object Constants {
  const val BASE_URL = "http://ec2-15-165-213-46.ap-northeast-2.compute.amazonaws.com:8080"
  const val APP_TAG = "LE-MORNING"
  const val DB_VERSION = 1

  const val X_ACCESS_TOKEN = "x-access-token"
  const val LAST_USER_EMAIL = "last-user-email"
  const val LAST_USER_PWD = "last-user-pwd"

  const val MEDIA_TYPE_FORM_DATA = "multipart/form-data"

  val DATETIME_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA)
  val DATE_FORMAT_YYYY_MM_DD = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

  // Password status
  const val PASSWORD_MALFORMED = 1000
  const val PASSWORD_AT_LEAST_ONE_EMPTY = 1001
  const val PASSWORD_SAME = 1002
  const val PASSWORD_DIFFERENT = 1003

  // Email status
  const val EMAIL_UNCHECKED = 1010
  const val EMAIL_EMPTY = 1011
  const val EMAIL_INVALID = 1012
  const val EMAIL_VALID = 1013

  // 성별
  const val MALE = "male"
  const val FEMALE = "Female"

  fun <T> apiError(default: T): BaseResponse<T> {
    return BaseResponse(code = 500, data = default, message = "Network Error", response = "failure")
  }
}