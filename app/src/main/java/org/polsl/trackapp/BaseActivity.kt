package org.polsl.trackapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import org.polsl.trackapp.form.FormActivity
import org.polsl.trackapp.model.Item
import org.polsl.trackapp.search.BookmarkActivity
import org.polsl.trackapp.search.SearchActivity


abstract class BaseActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {
    protected var navigationView: BottomNavigationView? = null

    private lateinit var database: DatabaseReference

    private var books: MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        navigationView = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigationView!!.setOnNavigationItemSelectedListener(this)

        setPagerAdapter()

        database = Firebase.database.reference

        //writeNewBook("1", "Pod kopułą", "Stephen King")
        //Toast.makeText(this, FirebaseAuth.getInstance().currentUser?.email, Toast.LENGTH_SHORT).show();
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

    fun selectBottomNavigationBarItem(itemId: Int) {
        val item: MenuItem = navigationView!!.menu.findItem(itemId)
        item.setChecked(true)
    }

    abstract fun getLayoutId(): Int // this is to return which layout(activity) needs to display when clicked on tabs.
    abstract fun getBottomNavigationMenuItemId(): Int //Which menu item selected and change the state of that menu item
    abstract fun setPagerAdapter()

    fun writeToDatabase(view: View) {
        val type = spinner2.selectedItem.toString()
        var state = ""
        if (switch_bookmark.isChecked == true) {
            state = "BOOKMARKED"
        } else {
            state = "UNBOOKMARKED"
        }
        val item: Item = createItem()
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child(userId).child(state).child(type).push().setValue(item)
            .addOnSuccessListener {
                Toast.makeText(this, "Write was successful", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Write failed successful", Toast.LENGTH_SHORT).show()
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

    private fun readBooks() {
        val booksListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                books.clear()
                //dataSnapshot.children.mapNotNullTo(books) { it.getValue<Item>(Item::class.java) }
                for (data in dataSnapshot.children){
                    val book = data.getValue(Item::class.java)
                    books.add(book!!)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child(userId).child("BOOKMARKED").child("Book")
            .addValueEventListener(booksListener)

    }

}