package org.polsl.trackapp.search

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
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

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, SearchActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

     override fun setPagerAdapter(){
         viewpager.adapter = TabsPagerAdapter(this)
     }
}
