package com.aslnstbk.komek.main.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslnstbk.komek.R
import com.aslnstbk.komek.main.presentation.view.MainAppNavigator
import com.aslnstbk.komek.main.presentation.viewModel.MainViewModel
import com.aslnstbk.komek.navigation.NavigationState
import com.aslnstbk.komek.navigation.Screens
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

lateinit var APP_ACTIVITY: MainActivity

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val navigatorHolder: NavigatorHolder by inject()
    private val router: Router by inject()

    private val navigator: Navigator = MainAppNavigator(this, R.id.activity_main_fragment_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        APP_ACTIVITY = this

        observeLiveData()
    }

    override fun onStart() {
        super.onStart()

        mainViewModel.setStartFragment()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    private fun observeLiveData() {
        mainViewModel.navigationLiveData.observe(this, ::handleNavigation)
    }

    private fun handleNavigation(navigationState: NavigationState) {
        when (navigationState) {
            is NavigationState.BaseFlow -> router.replaceScreen(Screens.BaseFlow())
            is NavigationState.Auth -> router.replaceScreen(Screens.Auth())
            else -> {}
        }
    }
}