package com.aslnstbk.komek.auth.data

import android.content.Intent
import com.aslnstbk.komek.auth.domain.AuthRepository
import com.aslnstbk.komek.common.domain.UserDataSource

class DefaultAuthRepository(
    private val userDataSource: UserDataSource
) : AuthRepository {

    override fun signIn(
        data: Intent?,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        userDataSource.signIn(
            data = data,
            onSuccess = onSuccess,
            onFail = onFail
        )
    }
}