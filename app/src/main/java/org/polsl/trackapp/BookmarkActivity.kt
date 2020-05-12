package org.polsl.trackapp

import kotlinx.android.synthetic.main.activity_bookmark.*

class BookmarkActivity : BaseActivity() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_bookmark)
//    }

    override fun getLayoutId(): Int {
        return R.layout.activity_bookmark
    }

    override fun getBottomNavigationMenuItemId(): Int {
        return R.id.action_bookmark
    }

//    override fun dupa(){
//        val fragmentAdapter = TabsPagerAdapter(supportFragmentManager)
//        viewpager.adapter = fragmentAdapter
//        sliding_tabs.setupWithViewPager(viewpager)
//    }
}
