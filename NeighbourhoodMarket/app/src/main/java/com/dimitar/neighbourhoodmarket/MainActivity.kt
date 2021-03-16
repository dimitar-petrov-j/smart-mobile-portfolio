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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    val database = Firebase.database
    val myRef = database.getReference("collection")

    private val menu: MutableList<Item> = mutableListOf()

    private fun addPostEventListener(postReference: DatabaseReference, recyclerView: RecyclerView) {

        val list = ArrayList<Item>()

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()

                for(e in dataSnapshot.children){
                    val item = e.getValue<Item>()
                    list.add(item!!)
                }

                val adapter = CustomAdapter(list)

                recyclerView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        postReference.addValueEventListener(postListener)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.button)
        val btnToCreate = findViewById<ImageButton>(R.id.imageButton)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        btn.setOnClickListener {
                val availableItems: List<Item> = mutableListOf(
                    Item("pain", 3),
                    Item("test", 4),
                    Item("paper", 5),
                    Item("toaster", 6)
                )
                availableItems.forEach {
                    val key = myRef.push().key
                    it.uuid = key!!
                    myRef.child(key).setValue(it)
                }
//            initItemMenu()
//            Log.i("LOG", menu.toString())
        }

        addPostEventListener(myRef, recyclerView)

        btnToCreate.setOnClickListener {
            val intent = Intent(this, CreateFormActivity::class.java)
            startActivity(intent);
        }
    }
}
