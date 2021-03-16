package com.dimitar.neighbourhoodmarket

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreateFormActivity: AppCompatActivity() {

    val database = Firebase.database
    val myRef = database.getReference("collection")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_form)

        val itemName = findViewById<EditText>(R.id.editTextItemName)
        val itemPrice = findViewById<EditText>(R.id.editTextItemPrice)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        btnAdd.setOnClickListener{
            val name: String = itemName.text.toString()
            val price: Long = itemPrice.text.toString().toLong()

            val item: Item = Item(name, price)

            val key = myRef.push().key
            item.uuid = key!!
            myRef.child(key).setValue(item)
        }


        val homeBtn = findViewById<ImageButton>(R.id.imageButton2)

        homeBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }
    }
}