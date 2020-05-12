package org.polsl.trackapp

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
}
