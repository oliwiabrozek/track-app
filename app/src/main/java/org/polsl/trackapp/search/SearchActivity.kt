package org.polsl.trackapp.search

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import org.polsl.trackapp.BaseActivity
import org.polsl.trackapp.R
import org.polsl.trackapp.SignInActivity

class SearchActivity : BaseActivity() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
//
//        //setupUI()
//    }
override fun getLayoutId(): Int {
    return R.layout.activity_home
}

    override fun getBottomNavigationMenuItemId(): Int {
        return R.id.action_search
    }

//    private fun setupUI() {
//        sign_out_button.setOnClickListener {
//            signOut()
//        }
//    }

    private fun signOut() {
        startActivity(
            SignInActivity.getLaunchIntent(
                this
            )
        )
        FirebaseAuth.getInstance().signOut();
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, SearchActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Handle item selection
        return when (item.getItemId()) {
            R.id.sign_out_menu_item -> {
                signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

     override fun setPagerAdapter(){
         viewpager.adapter = TabsPagerAdapter(this)
    }
}
