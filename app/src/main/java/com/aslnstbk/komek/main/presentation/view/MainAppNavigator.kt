package com.aslnstbk.komek.main.presentation.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.aslnstbk.komek.R
import com.aslnstbk.komek.auth.presentation.AuthFragment
import com.aslnstbk.komek.base_flow.BaseFlowFragment
import com.aslnstbk.komek.utils.hide
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.appbar.AppBarLayout

class MainAppNavigator(
    activity: FragmentActivity,
    container: Int
) : AppNavigator(activity, container) {

    private lateinit var appBarLayout: AppBarLayout

    override fun setupFragmentTransaction(
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment?
    ) {
        super.setupFragmentTransaction(fragmentTransaction, currentFragment, nextFragment)

        initViews()
        checkHideBottomNavFragment(currentFragment, nextFragment)
    }

    private fun initViews() {
        appBarLayout = activity.findViewById(R.id.activity_main_app_bar)
    }

    private fun checkHideBottomNavFragment(
        currentFragment: Fragment?,
        nextFragment: Fragment?
    ) {
        val currentFragments = checkFragments(currentFragment)
        val nextFragments = checkFragments(nextFragment)

        when (currentFragments) {
            true -> appBarLayout.hide()
            false -> {}
        }

        when (nextFragments) {
            true -> appBarLayout.hide()
            false -> {}
        }
    }

    private fun checkFragments(fragment: Fragment?): Boolean {
        return fragment is AuthFragment || fragment is BaseFlowFragment
    }
}