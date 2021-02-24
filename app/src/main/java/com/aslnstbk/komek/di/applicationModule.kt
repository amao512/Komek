package com.aslnstbk.komek.di

import com.aslnstbk.komek.common.data.DefaultImageLoader
import com.aslnstbk.komek.common.data.PeopleHelpFirebaseDataSource
import com.aslnstbk.komek.common.data.HelpNeedFirebaseDataSource
import com.aslnstbk.komek.common.data.UserFirebaseDataSource
import com.aslnstbk.komek.common.domain.PeopleHelpDataSource
import com.aslnstbk.komek.common.domain.HelpNeedDataSource
import com.aslnstbk.komek.common.domain.ImageLoader
import com.aslnstbk.komek.common.domain.UserDataSource
import org.koin.dsl.module

val applicationModule = module {

    single {
        DefaultImageLoader() as ImageLoader
    }

    factory {
        PeopleHelpFirebaseDataSource(
            helpNeedDataSource = get(),
            firebaseDatabase = get(),
            firebaseAuth = get(),
            helpNeedMapper = get()
        ) as PeopleHelpDataSource
    }

    factory {
        HelpNeedFirebaseDataSource(
            firebaseDatabase = get(),
            firebaseAuth = get(),
            helpNeedMapper = get()
        ) as HelpNeedDataSource
    }

    factory {
        UserFirebaseDataSource(
            firebaseDatabase = get(),
            firebaseAuth = get()
        ) as UserDataSource
    }
}