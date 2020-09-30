package com.example.gitapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var fragmentlist = arrayListOf<Fragment>()
    var titleList = arrayListOf<String>()

    fun populateFragment(fragment: Fragment, title: String) {
        fragmentlist.add(fragment)
        titleList.add(title)
    }

    override fun getItem(position: Int) = fragmentlist[position]

    override fun getCount() = fragmentlist.size

    override fun getPageTitle(position: Int) = titleList[position]
}