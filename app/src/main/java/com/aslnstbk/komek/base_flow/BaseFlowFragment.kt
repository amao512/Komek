package com.aslnstbk.komek.base_flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.aslnstbk.komek.R
import com.aslnstbk.komek.common.view.ToolbarBuilder
import com.aslnstbk.komek.home.presentation.HomeFragment
import com.aslnstbk.komek.main.presentation.view.ViewPagerAdapter
import com.aslnstbk.komek.profile.presentation.ProfileFragment
import com.google.android.material.tabs.TabLayout

class BaseFlowFragment : Fragment() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var homeFragment: HomeFragment
    private lateinit var profileFragment: ProfileFragment

    private lateinit var toolbar: Toolbar
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onStart() {
        super.onStart()

        ToolbarBuilder().hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_base_flow, container, false)

        initViews(view)
        initTabs()

        return view
    }

    private fun initViews(view: View) {
        toolbar = view.findViewById(R.id.fragment_base_flow_toolbar)
        tabLayout = view.findViewById(R.id.fragment_base_flow_tab_layout)
        viewPager = view.findViewById(R.id.fragment_base_flow_view_pager)
    }

    private fun initTabs() {
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, 0)
        homeFragment = HomeFragment()
        profileFragment = ProfileFragment()

        viewPagerAdapter.addFragment(homeFragment, getString(R.string.menu_item_main))
        viewPagerAdapter.addFragment(profileFragment, getString(R.string.menu_item_profile))
        viewPager.adapter = viewPagerAdapter

        tabLayout.setupWithViewPager(viewPager)
    }
}