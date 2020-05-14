package org.polsl.trackapp.search

import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.polsl.trackapp.search.SearchFragment

class TabsPagerAdapter (fa: FragmentActivity) : FragmentStateAdapter(fa){

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        SearchFragment()

}