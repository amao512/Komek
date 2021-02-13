package com.aslnstbk.komek.di

import com.aslnstbk.komek.common.data.DefaultImageLoader
import com.aslnstbk.komek.common.domain.ImageLoader
import org.koin.dsl.module

val applicationModule = module {

    single {
        DefaultImageLoader() as ImageLoader
    }
}