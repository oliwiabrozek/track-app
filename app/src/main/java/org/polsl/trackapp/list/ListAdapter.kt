package org.polsl.trackapp.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import org.polsl.trackapp.model.Item

class ListAdapter(private val list: MutableList<Item>, val itemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<ItemViewHolder>(), Filterable {
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
        override fun performFiltering(constraint: CharSequence): Filter.FilterResults? {
            var filteredList = mutableListOf<Item>()
            if(constraint == null || constraint.length == 0){
                filteredList.addAll(fullList)
            } else {
                val finalPattern: String = constraint.toString().toLowerCase().trim()

                for(item in fullList){
                    if(item.title.toLowerCase().contains(finalPattern)){
                        filteredList.add(item)
                    }
                }
            }

            val filterResults: FilterResults = FilterResults()
            filterResults.values = filteredList

            return filterResults
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            list.clear()
            list.addAll(results.values as MutableList<Item>)
            notifyDataSetChanged()
        }
    }
}

interface OnItemClickListener{
    fun onItemClicked(item: Item)
}