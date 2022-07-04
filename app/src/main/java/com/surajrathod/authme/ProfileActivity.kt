package com.surajrathod.authme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.surajrathod.authme.util.DataStore

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val token = intent.getStringExtra(DataStore.JWT_TOKEN)
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show() // remove it after testing

    }
}