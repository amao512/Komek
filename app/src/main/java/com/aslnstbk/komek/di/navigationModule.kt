package com.aslnstbk.komek.di

import com.aslnstbk.komek.KomekApplication
import org.koin.dsl.module

val navigationModule = module {

    single {
        KomekApplication.instance.navigatorHolder
    }

    single {
        KomekApplication.instance.router
    }
}