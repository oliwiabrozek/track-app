package org.polsl.trackapp.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.polsl.trackapp.R
import org.polsl.trackapp.model.Item

class ItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_item, parent, false)) {
    private var mTitleView: TextView? = null
    private var mYearView: TextView? = null
    private var mAuthorView: TextView? = null


    init {
        mTitleView = itemView.findViewById(R.id.item_title)
        mAuthorView = itemView.findViewById(R.id.item_author)
        mYearView = itemView.findViewById(R.id.item_year)
    }

    fun bind(item: Item, clickListener: OnItemClickListener) {
        mTitleView?.text = item.title
        mAuthorView?.text = item.author
        mYearView?.text = item.year.toString()

        itemView.setOnClickListener {
            clickListener.onItemClicked(item)
        }
    }

}