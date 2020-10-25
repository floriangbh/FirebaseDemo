package com.example.floriangbh.firebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference

import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.FirebaseDatabase



class MainActivity : AppCompatActivity() {

    // Var

    private var itemAdapter: ItemAdapter? = null

    private var items: ArrayList<ItemData> = ArrayList()

    private var databaseReference: DatabaseReference? = null

    // Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = FirebaseDatabase.getInstance()
        databaseReference = database.reference

        item_recycler.layoutManager = LinearLayoutManager(this)
        itemAdapter = ItemAdapter(items) { partItem: ItemData -> itemClicked(partItem) }
        itemAdapter.let { item_recycler.adapter = it }
    }

    override fun onStart() {
        super.onStart()

        val adapter = itemAdapter ?: return
        val databaseRef = databaseReference ?: return
        databaseRef.addChildEventListener(FirebaseEventListener(adapter))
    }

    // Action

    private fun itemClicked(item: ItemData) {
        println(item.id)
    }
}
