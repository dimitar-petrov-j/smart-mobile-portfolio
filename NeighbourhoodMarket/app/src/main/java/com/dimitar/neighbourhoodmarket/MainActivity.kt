package com.dimitar.neighbourhoodmarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
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


//    private fun initItemMenu() {
//        val menuListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                menu.clear()
//                dataSnapshot.children.mapNotNullTo(menu) { it.getValue<Item>(Item::class.java) }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                println("loadPost:onCancelled ${databaseError.toException()}")
//            }
//        }
//        myRef.addListenerForSingleValueEvent(menuListener)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.button)

        btn.setOnClickListener {
                val availableItems: List<Item> = mutableListOf(
                    Item("pain", 3.0),
                    Item("test", 4.0),
                    Item("paper", 5.0),
                    Item("toaster", 6.0)
                )
                availableItems.forEach {
                    val key = myRef.push().key
                    it.uuid = key!!
                    myRef.child(key).setValue(it)
                }
//            initItemMenu()
//            Log.i("LOG", menu.toString())
        }



//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//
//        menu.forEach {
//
//        }
    }
}
