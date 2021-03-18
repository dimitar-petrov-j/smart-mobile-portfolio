package com.dimitar.neighbourhoodmarket

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.dimitar.neighbourhoodmarket.model.Item
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.item_layout.*

class ItemActivity: AppCompatActivity() {

    private val database = Firebase.database
    private var myRef = database.getReference("collection")

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_layout)

        val animDrawable = item_gradient_layout.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(20)
        animDrawable.setExitFadeDuration(5000)
        animDrawable.start()

        val itemName = findViewById<TextView>(R.id.textItemName)
        val itemPrice = findViewById<TextView>(R.id.textItemPrice)

        val uuid = intent.getSerializableExtra("itemID").toString()
        val purchased: Boolean = intent.getSerializableExtra("isPurchased") as Boolean
        val clientInfo: String = intent.getSerializableExtra("contactInfo").toString()
        itemName.text = intent.getSerializableExtra("itemName").toString()
        itemPrice.text = "€" + intent.getSerializableExtra("itemPrice").toString()

        val purchaseBtn = findViewById<Button>(R.id.btnPurchase)

        purchaseBtn.setOnClickListener{
            if(!purchased){
                val itemRef = myRef.child(uuid)
                val item = Item(itemName.text.toString(), intent.getSerializableExtra("itemPrice").toString().toLong(), clientInfo, uuid, true)
                itemRef.setValue(item)

                val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    val channel = NotificationChannel("31",
                            "Hoodplaats",
                            NotificationManager.IMPORTANCE_DEFAULT)
                    channel.description = "Contact notifications"
                    mNotificationManager.createNotificationChannel(channel)
                }

                val mBuilder = NotificationCompat.Builder(applicationContext, "31")
                        .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                        .setContentTitle("Item has been marked for purchase.") // title for notification
                        .setContentText("Contact is at: $clientInfo")// message for notification
                        .setAutoCancel(true) // clear notification after click

                mNotificationManager.notify(0, mBuilder.build())
            }
            else{
                Toast.makeText(this, "Item is already marked for purchase", Toast.LENGTH_SHORT).show()
            }
        }

        val homeBtn = findViewById<ImageButton>(R.id.imageButtonHome)

        homeBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }
    }
}