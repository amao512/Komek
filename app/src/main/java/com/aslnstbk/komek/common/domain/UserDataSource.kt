package com.aslnstbk.komek.common.domain

import android.content.Intent

interface UserDataSource {

    fun signIn(
        data: Intent?,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    )
}