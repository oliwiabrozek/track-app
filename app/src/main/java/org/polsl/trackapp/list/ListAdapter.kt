package org.polsl.trackapp.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import org.polsl.trackapp.model.Item
import java.util.*

class ListAdapter(
    private val list: MutableList<Item>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ItemViewHolder>(), Filterable {
    private val fullList = mutableListOf<Item>()

    init {
        fullList.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Item = list[position]
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount(): Int = list.size

    override fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults? {
            val filteredList = mutableListOf<Item>()
            if (constraint.isEmpty()) {
                filteredList.addAll(fullList)
            } else {
                val finalPattern: String = constraint.toString().toLowerCase(Locale.ROOT).trim()

                for (item in fullList) {
                    if (item.title.toLowerCase(Locale.ROOT).contains(finalPattern)
                        || item.author.toLowerCase(Locale.ROOT).contains(finalPattern)
                        || item.year.toString().contains(finalPattern)
                    ) {
                        filteredList.add(item)
                    }
                }
            }

            val filterResults: FilterResults = FilterResults()
            filterResults.values = filteredList

            return filterResults
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            list.clear()
            list.addAll(results.values as MutableList<Item>)
            notifyDataSetChanged()
        }
    }
}

interface OnItemClickListener {
    fun onItemClicked(item: Item)
}