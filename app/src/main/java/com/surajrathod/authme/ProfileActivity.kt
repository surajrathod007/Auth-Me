package com.surajrathod.authme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.surajrathod.authme.util.DataStore

class ProfileActivity : AppCompatActivity() {

    lateinit var btnEdit : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_profile)

        val firstName = intent.getStringExtra("firstName")
        val user = intent.getSerializableExtra("user")
        Toast.makeText(this,"Hii ${firstName} ",Toast.LENGTH_SHORT).show()
        btnEdit = findViewById(R.id.btnEditProfile)
        btnEdit.setOnClickListener {
            val intent = Intent(this@ProfileActivity,ProfileEdit::class.java)
            intent.putExtra("user",user)
            startActivity(intent)
        }
        //Toast.makeText(this, token, Toast.LENGTH_SHORT).show() // remove it after testing

    }
}