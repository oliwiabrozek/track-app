package org.polsl.trackapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


abstract class BaseActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {
    protected var navigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        navigationView = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigationView!!.setOnNavigationItemSelectedListener(this)
    }

    //abstract fun getContentViewId(): Int

    override fun onStart() {
        super.onStart()
        updateNavigationBarState()
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    public override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigationView!!.postDelayed({
            val itemId: Int = item.getItemId()
            if (itemId == R.id.action_search) {
                startActivity(Intent(this, HomeActivity::class.java))
            } else if (itemId == R.id.action_bookmark) {
                startActivity(Intent(this, BookmarkActivity::class.java))
            } else if (itemId == R.id.action_add) {
                startActivity(Intent(this, FormActivity::class.java))
            }
            finish()
        }, 300)
        return true
    }

    private fun updateNavigationBarState() {
        val actionId: Int = getBottomNavigationMenuItemId()
        selectBottomNavigationBarItem(actionId)
    }

    //abstract fun getNavigationMenuItemId(): Int

    fun selectBottomNavigationBarItem(itemId: Int) {
        val item: MenuItem = navigationView!!.menu.findItem(itemId)
        item.setChecked(true)
    }

    abstract fun getLayoutId(): Int // this is to return which layout(activity) needs to display when clicked on tabs.
    abstract fun getBottomNavigationMenuItemId(): Int //Which menu item selected and change the state of that menu item

}