package com.aslnstbk.komek.auth.domain

interface AuthRepository {

    fun signIn(
        idToken: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    )
}