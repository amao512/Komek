package com.aslnstbk.komek.profile.di

import com.aslnstbk.komek.profile.presentation.viewModel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {

    viewModel {
        ProfileViewModel(
            firebaseAuth = get()
        )
    }
}