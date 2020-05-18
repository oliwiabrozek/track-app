package org.polsl.trackapp.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class TabsPagerAdapter (fa: FragmentActivity) : FragmentStateAdapter(fa){

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        println("----------------------------TAB POSITION--------------------------------------")
        println(position)
        val bundle = Bundle()
        bundle.putInt("Tab", position)
        val fragment = SearchFragment()
        fragment.arguments=bundle
        return fragment
    }



}