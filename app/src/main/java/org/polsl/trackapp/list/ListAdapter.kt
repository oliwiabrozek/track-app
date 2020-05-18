package org.polsl.trackapp.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.polsl.trackapp.model.Item

class ListAdapter(private val list: MutableList<Item>)
    : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

}