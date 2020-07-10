package org.prography.lemorning.config

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val MEDIA_TYPE_FORM_DATA = "multipart/form-data"
    const val MEDIA_TYPE_IMAGE = "image/*"
    const val MEDIA_TYPE_BYTE8 = "application/octet-stream"
    val DATE_FORMAT_YYYY_MM_DD = SimpleDateFormat("YYYY-MM-dd", Locale.KOREA)

    const val PASSWORD_MALFORMED = 1000
    const val PASSWORD_AT_LEAST_ONE_EMPTY = 1001
    const val PASSWORD_SAME = 1002
    const val PASSWORD_DIFFERENT = 1003

    const val EMAIL_UNCHECKED = 1010
    const val EMAIL_EMPTY = 1011
    const val EMAIL_INVALID = 1012
    const val EMAIL_VALID = 1013

    const val MALE = "male"
    const val FEMALE = "Female"
}