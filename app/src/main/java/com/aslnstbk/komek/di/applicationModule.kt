package com.aslnstbk.komek.di

import com.aslnstbk.komek.common.data.DefaultImageLoader
import com.aslnstbk.komek.common.data.HelpFirebaseDataSource
import com.aslnstbk.komek.common.data.UserFirebaseDataSource
import com.aslnstbk.komek.common.domain.HelpDataSource
import com.aslnstbk.komek.common.domain.ImageLoader
import com.aslnstbk.komek.common.domain.UserDataSource
import org.koin.dsl.module

val applicationModule = module {

    single {
        DefaultImageLoader() as ImageLoader
    }

    factory {
        HelpFirebaseDataSource(
            firebaseDatabase = get(),
            firebaseAuth = get(),
            helpNeedMapper = get()
        ) as HelpDataSource
    }

    factory {
        UserFirebaseDataSource(
            firebaseDatabase = get(),
            firebaseAuth = get()
        ) as UserDataSource
    }
}