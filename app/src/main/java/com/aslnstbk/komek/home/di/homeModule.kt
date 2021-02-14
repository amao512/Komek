package com.aslnstbk.komek.home.di

import com.aslnstbk.komek.home.data.DefaultHomeRepository
import com.aslnstbk.komek.home.domain.HomeRepository
import com.aslnstbk.komek.home.presentation.viewModel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    viewModel {
        HomeViewModel(
            homeRepository = get()
        )
    }

    factory {
        DefaultHomeRepository(
            firebaseClient = get(),
            firebaseDatabase = get()
        ) as HomeRepository
    }
}