package com.aslnstbk.komek.di

import com.aslnstbk.komek.common.data.FirebaseClient
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val firebaseModule = module {

    single {
        FirebaseClient()
    }

    single {
        val firebaseClient: FirebaseClient = get()

        firebaseClient.googleSignInClient
    }

    single {
        Firebase.auth
    }

    factory {
        Firebase.database
    }
}