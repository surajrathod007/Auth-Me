package com.surajrathod.authme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ProfileActivity : AppCompatActivity() {

    lateinit var btnEditProfile : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        btnEditProfile = findViewById(R.id.btnEditProfile)

        btnEditProfile.setOnClickListener {
            val intnet = Intent(this@ProfileActivity,ProfileEdit::class.java)
            startActivity(intnet)
        }

    }
}