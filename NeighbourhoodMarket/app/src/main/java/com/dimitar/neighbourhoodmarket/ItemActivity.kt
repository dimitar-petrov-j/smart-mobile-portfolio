package com.dimitar.neighbourhoodmarket

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dimitar.neighbourhoodmarket.model.Item

class ItemActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_layout)

        val itemName = findViewById<TextView>(R.id.textItemName)
        val itemPrice = findViewById<TextView>(R.id.textItemPrice)

        itemName.text = intent.getSerializableExtra("itemName").toString()
        itemPrice.text = intent.getSerializableExtra("itemPrice").toString()

        val homeBtn = findViewById<ImageButton>(R.id.imageButtonHome)

        homeBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }
    }
}