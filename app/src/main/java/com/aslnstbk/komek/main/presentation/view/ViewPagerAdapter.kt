package com.aslnstbk.komek.main.presentation.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(
    fm: FragmentManager,
    behavior: Int
) : FragmentPagerAdapter(fm, behavior) {

    private val fragments: MutableList<Fragment> = mutableListOf()
    private val fragmentsTitle: MutableList<String> = mutableListOf()

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentsTitle[position]
    }

    fun addFragment(
        fragment: Fragment,
        fragmentTitle: String
    ) {
        fragments.add(fragment)
        fragmentsTitle.add(fragmentTitle)
    }
}