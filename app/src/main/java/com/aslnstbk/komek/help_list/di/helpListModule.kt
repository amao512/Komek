package com.aslnstbk.komek.help_list.di

import com.aslnstbk.komek.help_list.data.DefaultHelpListRepository
import com.aslnstbk.komek.help_list.domain.HelpListRepository
import com.aslnstbk.komek.help_list.presentation.viewModel.HelpListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val helpListModule = module {

    viewModel {
        HelpListViewModel(
            helpListRepository = get()
        )
    }

    factory {
        DefaultHelpListRepository(
            firebaseClient = get(),
            firebaseDatabase = get(),
            helpNeedMapper = get()
        ) as HelpListRepository
    }
}