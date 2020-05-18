package org.polsl.trackapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.polsl.trackapp.form.FormActivity
import org.polsl.trackapp.model.Item
import org.polsl.trackapp.search.BookmarkActivity
import org.polsl.trackapp.search.SearchActivity


abstract class BaseActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {
    protected var navigationView: BottomNavigationView? = null

    private lateinit var database: DatabaseReference

    private var mode: String = ""

    private var item: Item? = null
    private var type: String? = null
    private var state: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        navigationView = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigationView!!.setOnNavigationItemSelectedListener(this)

        setPagerAdapter()

        database = Firebase.database.reference

        item = intent.getSerializableExtra("ITEM") as? Item
        state = intent.getStringExtra("itemState")
        type = intent.getStringExtra("itemType")



        if (item != null) {
            button_delete.isEnabled = true
            fillFields()
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

    private fun signOut() {
        startActivity(
            SignInActivity.getLaunchIntent(
                this
            )
        )
        FirebaseAuth.getInstance().signOut();
    }

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
                startActivity(Intent(this, SearchActivity::class.java))
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

    private fun selectBottomNavigationBarItem(itemId: Int) {
        val item: MenuItem = navigationView!!.menu.findItem(itemId)
        item.isChecked = true
    }

    abstract fun getLayoutId(): Int // this is to return which layout(activity) needs to display when clicked on tabs.
    abstract fun getBottomNavigationMenuItemId(): Int //Which menu item selected and change the state of that menu item
    abstract fun setPagerAdapter()

    fun writeToDatabase(view: View) {
        val type = spinner2.selectedItem.toString()
        var state = ""
        if (switch_bookmark.isChecked) {
            state = "BOOKMARKED"
        } else {
            state = "UNBOOKMARKED"
        }
        val item: Item = createItem()
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child(userId).child(state).child(type).push().setValue(item)
            .addOnSuccessListener {
                Toast.makeText(this, "Write was successful", Toast.LENGTH_SHORT).show()
                clearFields()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Write failed", Toast.LENGTH_SHORT).show()
            }

    }

    private fun createItem(): Item {
        val author: String = author_edit_text.text.toString()
        val title: String = title_edit_text.text.toString()
        val yearString: String = year_edit_text.text.toString()
        val year: Int? = if (yearString.isEmpty()) {
            null;
        } else {
            Integer.parseInt(yearString)
        }
        return Item(author, title, year)
    }

    fun deleteItem(view: View) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        if (item!!.firebaseKey != null && state != null && type != null) {
            database.child(userId).child(state!!).child(type!!).child(item!!.firebaseKey!!)
                .removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Delete was successful", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun clearFields() {
        author_edit_text.text.clear()
        title_edit_text.text.clear()
        year_edit_text.text.clear()
    }

    private fun fillFields() {
        author_edit_text.setText(item?.author)
        title_edit_text.setText(item?.title)
        if (item?.year != null) {
            year_edit_text.setText(item!!.year!!.toString())
        }
        switch_bookmark.isChecked = state == "BOOKMARKED"
        when (type) {
            "Movie" -> spinner2.setSelection(0)
            "Book" -> spinner2.setSelection(1)
            "Game" -> spinner2.setSelection(2)
        }

    }

}