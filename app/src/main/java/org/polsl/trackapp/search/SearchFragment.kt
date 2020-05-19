package org.polsl.trackapp.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_item_list.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.polsl.trackapp.Node
import org.polsl.trackapp.R
import org.polsl.trackapp.form.FormActivity
import org.polsl.trackapp.list.ListAdapter
import org.polsl.trackapp.list.OnItemClickListener
import org.polsl.trackapp.model.Item

private const val TAB_PARAM = "Tab"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment(), OnItemClickListener {
    private var tabParam: Int? = null

    private lateinit var database: DatabaseReference

    private var items: MutableList<Item> = mutableListOf()
    private val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private val activitySimpleName: String? = activity?.javaClass?.simpleName

    private var state: String = ""
    private var type: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tabParam = it.getInt(TAB_PARAM)
        }

        database = Firebase.database.reference

        read()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SearchFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(TAB_PARAM, param1)
                }
            }
    }

    private fun read() {
        val booksListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                items.clear()
                //dataSnapshot.children.mapNotNullTo(books) { it.getValue<Item>(Item::class.java) }
                for (data in dataSnapshot.children) {
                    val book = data.getValue(Item::class.java)
                    book?.firebaseKey = data.key
                    items.add(book!!)
                }

                //RecyclerView node initialized here
                if (activity != null) {
                    list.apply {
                        // set a LinearLayoutManager to handle Android
                        // RecyclerView behavior
                        layoutManager = LinearLayoutManager(activity)
                        // set the custom adapter to the RecyclerView
                        adapter = ListAdapter(items, this@SearchFragment)

                        search_bar.imeOptions = EditorInfo.IME_ACTION_DONE
                        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                            override fun onQueryTextChange(newText: String): Boolean {
                                (adapter as ListAdapter).filter.filter(newText)
                                return false
                            }

                            override fun onQueryTextSubmit(query: String): Boolean {
                                return false
                            }
                        })
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }

        val activitySimpleName: String? = activity?.javaClass?.simpleName

        checkItemState(activitySimpleName)
        checkItemType(tabParam)

        database.child(userId).child(state).child(type).addValueEventListener(booksListener)
    }

    private fun checkItemType(param: Int?) {
        if (param != null) {
            when (param) {
                0 -> type = Node.movie
                1 -> type = Node.book
                2 -> type = Node.game
            }
        }
    }

    private fun checkItemState(param: String?) {
        if (param !== null) {
            when (param) {
                "SearchActivity" -> {
                    state = Node.unbookmarked
                }
                "BookmarkActivity" -> {
                    state = Node.bookmarked
                }
            }
        }
    }

    override fun onItemClicked(item: Item) {
        val intent = Intent(context, FormActivity::class.java)

        checkItemType(tabParam)
        checkItemState(activitySimpleName)

        intent.putExtra("ITEM_TYPE", type)
        intent.putExtra("ITEM_STATE", state)
        intent.putExtra("ITEM", item)

        startActivity(intent)
    }
}
