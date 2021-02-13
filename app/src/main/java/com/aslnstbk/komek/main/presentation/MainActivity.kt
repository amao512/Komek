package com.aslnstbk.komek.main.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.aslnstbk.komek.R
import com.aslnstbk.komek.ask_help.presentation.AskHelpFragment
import com.aslnstbk.komek.auth.presentation.AuthFragment
import com.aslnstbk.komek.help_list.presentation.HelpListFragment
import com.aslnstbk.komek.help_need.presentation.HelpNeedFragment
import com.aslnstbk.komek.main.presentation.viewModel.MainViewModel
import com.aslnstbk.komek.navigation.Screens
import com.aslnstbk.komek.utils.hide
import com.aslnstbk.komek.utils.show
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

lateinit var APP_ACTIVITY: MainActivity

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val navigatorHolder: NavigatorHolder by inject()

    private val navigator: Navigator = object : AppNavigator(this, R.id.activity_main_fragment_container) {

        override fun setupFragmentTransaction(
            fragmentTransaction: FragmentTransaction,
            currentFragment: Fragment?,
            nextFragment: Fragment?
        ) {
            super.setupFragmentTransaction(fragmentTransaction, currentFragment, nextFragment)
            checkHideBottomNavFragment(currentFragment, nextFragment)
        }
    }

    private lateinit var appBarLayout: AppBarLayout
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        APP_ACTIVITY = this

        initViews()
        selectMenuItem()
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

    private fun initViews() {
        appBarLayout = findViewById(R.id.activity_main_app_bar)
        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation_view)
    }

    private fun selectMenuItem() {
        bottomNavigationView.selectedItemId = R.id.bottom_navigation_menu_home_item

        bottomNavigationView.setOnNavigationItemSelectedListener {
            val screen: FragmentScreen = when (it.itemId) {
                R.id.bottom_navigation_menu_profile_item -> Screens.Profile()
                R.id.bottom_navigation_menu_home_item -> Screens.Home()
                R.id.bottom_navigation_menu_chat_item -> Screens.Profile()
                else -> Screens.Profile()
            }

            mainViewModel.selectMenuItem(screen)

            true
        }
    }

    private fun checkHideBottomNavFragment(
        currentFragment: Fragment?,
        nextFragment: Fragment?
    ) {
        val currentFragments = checkFragments(currentFragment)
        val nextFragments = checkFragments(nextFragment)

        when (currentFragments) {
            true -> bottomNavigationView.hide()
            false -> bottomNavigationView.show()
        }

        when (nextFragments) {
            true -> bottomNavigationView.hide()
            false -> bottomNavigationView.show()
        }
    }

    private fun checkFragments(fragment: Fragment?): Boolean {
        return fragment is AuthFragment || fragment is HelpListFragment ||
                fragment is AskHelpFragment || fragment is HelpNeedFragment
    }
}