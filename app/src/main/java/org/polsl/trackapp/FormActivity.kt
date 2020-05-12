package org.polsl.trackapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class FormActivity : BaseActivity() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_form)
//    }

    override fun getLayoutId(): Int {
        return R.layout.activity_form
    }

    override fun getBottomNavigationMenuItemId(): Int {
        return R.id.action_add
    }
}
