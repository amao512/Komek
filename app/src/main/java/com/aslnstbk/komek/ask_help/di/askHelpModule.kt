package com.aslnstbk.komek.ask_help.di

import com.aslnstbk.komek.ask_help.data.DefaultAskHelpRepository
import com.aslnstbk.komek.ask_help.domain.AskHelpRepository
import com.aslnstbk.komek.ask_help.presentation.viewModel.AskHelpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val askHelpModule = module {

    viewModel {
        AskHelpViewModel(
            router = get(),
            askHelpRepository = get(),
            firebaseAuth = get()
        )
    }

    factory {
        DefaultAskHelpRepository(
            firebaseDatabase = get()
        ) as AskHelpRepository
    }
}