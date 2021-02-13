package com.aslnstbk.komek.di

import com.aslnstbk.komek.utils.mappers.HelpNeedMapper
import org.koin.dsl.module

val mappersModule = module {

    factory {
        HelpNeedMapper()
    }
}