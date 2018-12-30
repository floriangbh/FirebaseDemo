package com.example.floriangbh.firebase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference

import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.FirebaseDatabase



class MainActivity : AppCompatActivity() {

    var itemAdapter: ItemAdapter? = null
    var items: ArrayList<ItemData> = ArrayList()
    var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = FirebaseDatabase.getInstance()
        databaseReference = database.reference

        item_recycler.layoutManager = LinearLayoutManager(this)
        itemAdapter = ItemAdapter(items, { partItem : ItemData -> itemClicked(partItem) })
        itemAdapter.let { item_recycler.adapter = it }
    }

    override fun onStart() {
        super.onStart()

        val adapter = itemAdapter ?: return
        var databaseRef = databaseReference ?: return
        databaseRef.addChildEventListener(FirebaseEventListener(adapter))
    }

    fun itemClicked(item: ItemData) {
        println(item.id)
    }
}
