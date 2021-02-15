package com.aslnstbk.komek

import android.app.Application
import com.aslnstbk.komek.ask_help.di.askHelpModule
import com.aslnstbk.komek.auth.di.authModule
import com.aslnstbk.komek.di.applicationModule
import com.aslnstbk.komek.di.firebaseModule
import com.aslnstbk.komek.di.mappersModule
import com.aslnstbk.komek.di.navigationModule
import com.aslnstbk.komek.help_list.di.helpListModule
import com.aslnstbk.komek.help_need.di.helpNeedModule
import com.aslnstbk.komek.main.di.mainModule
import com.aslnstbk.komek.profile.di.profileModule
import com.github.terrakok.cicerone.Cicerone
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KomekApplication : Application() {

    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()

        instance = this

        FirebaseApp.initializeApp(this)

        startKoin {
            androidContext(this@KomekApplication)

            modules(
                applicationModule,
                firebaseModule,
                navigationModule,
                mappersModule,
                mainModule,
                authModule,
                profileModule,
                askHelpModule,
                helpListModule,
                helpNeedModule
            )
        }
    }

    companion object {
        lateinit var instance: KomekApplication
    }
}