package com.aslnstbk.komek.main.di

import com.aslnstbk.komek.main.presentation.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    viewModel {
        MainViewModel(
            firebaseAuth = get()
        )
    }
}