package com.aslnstbk.komek.auth.di

import com.aslnstbk.komek.auth.data.DefaultAuthRepository
import com.aslnstbk.komek.auth.domain.AuthRepository
import com.aslnstbk.komek.auth.presentation.viewModel.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {

    viewModel {
        AuthViewModel(
            router = get(),
            authRepository = get()
        )
    }

    factory {
        DefaultAuthRepository(
            firebaseAuth = get(),
            firebaseDatabase = get()
        ) as AuthRepository
    }
}