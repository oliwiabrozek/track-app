package org.polsl.trackapp.search

import android.content.Context
import android.content.Intent
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_home.*
import org.polsl.trackapp.BaseActivity
import org.polsl.trackapp.R

class SearchActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun getBottomNavigationMenuItemId(): Int {
        return R.id.action_search
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, SearchActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
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
