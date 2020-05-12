package org.polsl.trackapp

import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsPagerAdapter (fa: FragmentActivity) : FragmentStateAdapter(fa){



//    override fun getItem(position: Int): Fragment {
//        if(position == 0){
//            println(0)
//            return BlankFragment()
//        }
//        else {
//            return FormFragment()
//        }
//    }
//
//    override fun getCount(): Int {
//        return 2
//    }
//
//    override fun getPageTitle(position: Int): CharSequence? {
//        println("gettitle")
//        return when (position) {
//            0 -> {
//                "FILM"
//            }
//            else -> {
//                "KSIĄŻKA"
//            }
//        }
//    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = BlankFragment()


}