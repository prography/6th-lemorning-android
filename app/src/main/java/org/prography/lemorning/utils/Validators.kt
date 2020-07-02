package org.prography.lemorning.utils

import java.util.regex.Pattern

object Validators {

    @JvmStatic
    fun isValidEmail(src : String?) : Boolean =
        Pattern.matches("^[a-zA-Z0-9%+-._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$" ,src ?: "")

    @JvmStatic
    fun isValidPhone() {

    }
}