package com.example.floriangbh.firebase

import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ChildEventListener


class FirebaseEventListener(private val itemListAdapter: ItemAdapter) : ChildEventListener {

    override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
        val newItem = ItemData(dataSnapshot.value as String? ?: "" , dataSnapshot.key ?: "")
        if (!itemListAdapter.partItemList.contains(newItem)) {
            itemListAdapter.addItem(newItem)
            itemListAdapter.notifyDataSetChanged()
        }
    }

    override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
        // Child moved
        // ...
    }

    override fun onChildRemoved(dataSnapshot: DataSnapshot) {
        itemListAdapter.removeItem(dataSnapshot.key ?: "")
        itemListAdapter.notifyDataSetChanged()
    }

    override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
        // Child changed
        // ...
    }

    override fun onCancelled(databaseError: DatabaseError) {
        // Error
        // ...
    }
}