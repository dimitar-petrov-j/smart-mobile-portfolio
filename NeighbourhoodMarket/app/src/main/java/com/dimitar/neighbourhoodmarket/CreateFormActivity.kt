package com.dimitar.neighbourhoodmarket

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import com.dimitar.neighbourhoodmarket.model.Item
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CreateFormActivity: AppCompatActivity() {

    private val database = Firebase.database
    private val storage = Firebase.storage

    var storageRef = storage.reference
    var imagesRef = storageRef.child("images/image.jpeg")
    private val myRef = database.getReference("collection")

    private val REQUEST_IMAGE_CAPTURE = 1
    private var uri: Uri? = null
    lateinit var imageView: ImageView
    lateinit var currentPhotoPath: String


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
            imageView.isVisible = true;
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_form)

        val itemName = findViewById<EditText>(R.id.editTextItemName)
        val itemPrice = findViewById<EditText>(R.id.editTextItemPrice)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnCamera = findViewById<ImageButton>(R.id.imgBtnCamera)
        imageView = findViewById<ImageView>(R.id.imageView2)

        btnAdd.setOnClickListener{
            val name: String = itemName.text.toString()
            val price: Long = itemPrice.text.toString().toLong()

            val item: Item = Item(name, price)

            val key = myRef.push().key
            item.uuid = key!!
            myRef.child(key).setValue(item)

            imagesRef = storageRef.child("images/${uri!!.lastPathSegment}")

            imagesRef.putFile(uri!!)
        }

        btnCamera.setOnClickListener {

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                // Ensure that there's a camera activity to handle the intent
                takePictureIntent.resolveActivity(packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "com.dimitar.neighbourhoodmarket",
                            it
                        )
                        uri = photoURI
                    }
                }
            }
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        }


        val homeBtn = findViewById<ImageButton>(R.id.imageButton2)

        homeBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }
    }
}