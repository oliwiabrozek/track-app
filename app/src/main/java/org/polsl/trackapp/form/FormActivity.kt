package org.polsl.trackapp.form

import android.widget.ArrayAdapter
import android.widget.Spinner
import org.polsl.trackapp.BaseActivity
import org.polsl.trackapp.R

class FormActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_form
    }

    override fun getBottomNavigationMenuItemId(): Int {
        return R.id.action_add
    }

    override fun setPagerAdapter(){

    }
}
