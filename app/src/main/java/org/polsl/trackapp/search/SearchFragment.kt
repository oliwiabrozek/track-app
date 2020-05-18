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
import org.polsl.trackapp.R
import org.polsl.trackapp.form.FormActivity
import org.polsl.trackapp.list.ListAdapter
import org.polsl.trackapp.list.OnItemClickListener
import org.polsl.trackapp.model.Item
import java.text.Normalizer

private const val ARG_PARAM1 = "Tab"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment(), OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: String? = null

    private lateinit var database: DatabaseReference

    private var items: MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }

        database = Firebase.database.reference

        read()


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        println("-------------------------INFLATE-------------------------------------")
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
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
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
                        println("ADAPTER")
                        println(adapter)
                        adapter = ListAdapter(items, this@SearchFragment)


                        search_bar.imeOptions = EditorInfo.IME_ACTION_DONE
                        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                            override fun onQueryTextChange(newText: String): Boolean {
                                (adapter as ListAdapter).getFilter().filter(newText)
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
    var pathString = ""
    var pathString2 = ""

    if (activitySimpleName !== null)
    {
        if (activitySimpleName == "SearchActivity") {
            pathString = "UNBOOKMARKED"
        }
        if (activitySimpleName == "BookmarkActivity") {
            pathString = "BOOKMARKED"
        }
    }

    if (param1 != null)
    {
        when (param1) {
            0 -> pathString2 = "Movie"
            1 -> pathString2 = "Book"
            2 -> pathString2 = "Game"
        }
    }

    if (activitySimpleName !== null)
    {
        if (activitySimpleName == "SearchActivity") {
            pathString = "UNBOOKMARKED"
        }
        if (activitySimpleName == "BookmarkActivity") {
            pathString = "BOOKMARKED"
        }
    }

    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    database.child(userId).child(pathString).child(pathString2)
    .addValueEventListener(booksListener)

    println(pathString2)
}

override fun onItemClicked(item: Item) {
    val intent = Intent(context, FormActivity::class.java)

    var type: String = ""
    var state: String = ""

    val activitySimpleName: String? = activity?.javaClass?.simpleName

    if (param1 != null) {
        when (param1) {
            0 -> type = "Movie"
            1 -> type = "Book"
            2 -> type = "Game"
        }
    }

    if (activitySimpleName !== null) {
        if (activitySimpleName == "SearchActivity") {
            state = "UNBOOKMARKED"
        }
        if (activitySimpleName == "BookmarkActivity") {
            state = "BOOKMARKED"
        }
    }


    intent.putExtra("itemType", type)
    intent.putExtra("itemState", state)
    intent.putExtra("ITEM", item)
    startActivity(intent)
//        val bundle = Bundle()
//        bundle.putSerializable("ITEM", item)
//
//        startActivity(Intent(activity, FormActivity::class.java))

}
}
