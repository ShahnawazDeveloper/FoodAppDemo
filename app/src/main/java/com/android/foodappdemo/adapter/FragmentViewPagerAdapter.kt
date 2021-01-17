package com.android.foodappdemo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val fragmentList: ArrayList<Fragment>,
    private val lifecycle: Lifecycle
) : FragmentStateAdapter(
    fragmentManager, lifecycle
) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}
