package com.class100.tvsdk.ui.welcome

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class WelcomePagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = lazy {
        mutableListOf<Fragment>()
    }

    fun addFragment(vararg fragment: Fragment): WelcomePagerAdapter {
        fragments.value.addAll(fragment)
        return this
    }

    override fun getItem(position: Int): Fragment {
        return fragments.value[position]
    }

    override fun getCount(): Int {
        return fragments.value.size
    }

}