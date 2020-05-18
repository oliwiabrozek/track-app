package org.polsl.trackapp.search

import kotlinx.android.synthetic.main.activity_bookmark.viewpager
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
    }
}
