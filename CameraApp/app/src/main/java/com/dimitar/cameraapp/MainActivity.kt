package com.dimitar.cameraapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.util.*


class MainActivity : AppCompatActivity() {

    private val cameraRequest = 1888
    private var uri: Uri? = null
    private var storage: FirebaseStorage? = null
    private var mStorageRef: StorageReference? = null
    lateinit var imageView: ImageView
    lateinit var uploadButton: Button

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequest) {
            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(photo)
            uploadButton.isVisible = true;
            uri = getImageUri(applicationContext,photo);

        }
    }

    fun getImageUri(inContext: Context?, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            this@MainActivity.contentResolver,
            inImage,
            UUID.randomUUID().toString() + ".png",
            "drawing"
        )
        return Uri.parse(path)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mStorageRef = FirebaseStorage.getInstance().reference;

        if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), cameraRequest)
        imageView = findViewById(R.id.imageView)
        uploadButton = findViewById(R.id.buttonUpload)
        val photoButton: Button = findViewById(R.id.button)

        photoButton.setOnClickListener {

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequest)
        }

        uploadButton.setOnClickListener {

            mStorageRef!!.putFile(uri!!)

        }
    }
}