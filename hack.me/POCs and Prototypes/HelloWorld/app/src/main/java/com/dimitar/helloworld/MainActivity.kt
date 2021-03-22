package com.dimitar.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById(R.id.button) as Button
        val emailView = findViewById(R.id.editTextTextEmailAddress) as EditText
        val passwordView = findViewById(R.id.editTextTextPassword) as EditText
        val worldView = findViewById(R.id.helloworld) as TextView

        btn.setOnClickListener {
            val email = emailView.text.toString()
            val pass = passwordView.text.toString()
            btn.isVisible = false
            emailView.isVisible = false
            passwordView.isVisible = false
            worldView.text = "Hello, " + email;
        }
    }
}