package com.aslnstbk.komek.person_help.di

import com.aslnstbk.komek.person_help.data.DefaultPersonHelpRepository
import com.aslnstbk.komek.person_help.domain.PersonHelpRepository
import com.aslnstbk.komek.person_help.presentation.viewModel.PersonHelpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val personHelpModule = module {

    viewModel {
        PersonHelpViewModel(
            personHelpRepository = get()
        )
    }

    factory {
        DefaultPersonHelpRepository(
            peopleHelpDataSource = get()
        ) as PersonHelpRepository
    }
}