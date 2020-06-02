package org.prography.lemorning.utils

class BaseEvent<T : Any>(var data : T, var handled : Boolean = false) {
    fun get() : T? = when {
        !handled -> {
            handled = true
            data
        }
        else -> null
    }
}