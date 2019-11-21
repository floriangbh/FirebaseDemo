package com.example.floriangbh.firebase

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*

class ItemAdapter (val partItemList: ArrayList<ItemData>, private val clickListener: (ItemData) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return PartViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PartViewHolder).bind(partItemList[position], clickListener)
    }

    override fun getItemCount() = partItemList.size

    fun addItem(item: ItemData) {
        partItemList.add(item)
    }

    fun removeItem(itemId: String) {
        val item: ItemData? = partItemList.find { itemData -> itemData.itemName == itemId }
        item.let { partItemList.remove(it) }
    }

    class PartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(part: ItemData, clickListener: (ItemData) -> Unit) {
            itemView.name_id.text = part.itemName
            itemView.identifier_id.text = part.id
            itemView.setOnClickListener { clickListener(part)}
        }
    }
}