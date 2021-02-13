package com.aslnstbk.komek.utils

import android.widget.Toast
import com.aslnstbk.komek.main.presentation.APP_ACTIVITY
import kotlin.random.Random

fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY.applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun generateRandomString(): String {
    val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    return (1..30)
        .map { _ -> Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}