package org.polsl.trackapp.search

import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_bookmark.*
import org.polsl.trackapp.BaseActivity
import org.polsl.trackapp.R

class BookmarkActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_bookmark
    }

    override fun getBottomNavigationMenuItemId(): Int {
        return R.id.action_bookmark
    }

    override fun setPagerAdapter() {
        viewpager.adapter = TabsPagerAdapter(this)
        viewpager.isUserInputEnabled = false
        sliding_tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewpager.setCurrentItem(tab!!.position, false)
            }
        })
    }
}
