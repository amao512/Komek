package com.aslnstbk.komek.help_need.di

import com.aslnstbk.komek.help_need.data.DefaultHelpNeedRepository
import com.aslnstbk.komek.help_need.domain.HelpNeedRepository
import com.aslnstbk.komek.help_need.presentation.viewModel.HelpNeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val helpNeedModule = module {

    viewModel {
        HelpNeedViewModel(
            helpNeedRepository = get(),
            firebaseAuth = get()
        )
    }

    factory {
        DefaultHelpNeedRepository(
            helpNeedDataSource = get(),
            peopleHelpDataSource = get()
        ) as HelpNeedRepository
    }
}