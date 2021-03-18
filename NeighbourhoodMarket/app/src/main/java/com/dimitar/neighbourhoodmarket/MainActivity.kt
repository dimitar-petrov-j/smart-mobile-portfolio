package com.dimitar.neighbourhoodmarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimitar.neighbourhoodmarket.adapter.CustomAdapter
import com.dimitar.neighbourhoodmarket.model.Item
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val database = Firebase.database
    private val myRef = database.getReference("collection")

    private fun addPostEventListener(postReference: DatabaseReference, recyclerView: RecyclerView) {

        val list = ArrayList<Item>()

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()

                for(e in dataSnapshot.children){
                    val item = e.getValue<Item>()
                    list.add(item!!)
                }

                val adapter = CustomAdapter(list) { item -> onItemClicker(item) }

                recyclerView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        postReference.addValueEventListener(postListener)
    }

    private fun onItemClicker(item: Item){
        val intent = Intent(this, ItemActivity::class.java)
        intent.putExtra("itemName", item.item)
        intent.putExtra("itemPrice", item.price)
        intent.putExtra("contactInfo", item.clientInfo)
        intent.putExtra("itemID", item.uuid)
        intent.putExtra("isPurchased", item.isTaken)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnToCreate = findViewById<ImageButton>(R.id.imageButton)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)



        addPostEventListener(myRef, recyclerView)

        btnToCreate.setOnClickListener {
            val intent = Intent(this, CreateFormActivity::class.java)
            startActivity(intent)
        }
    }
}
