package com.surajrathod.authme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.surajrathod.authme.databinding.ActivityProfileEditBinding
import com.surajrathod.authme.model.User

class ProfileEdit : AppCompatActivity() {

    lateinit var binding: ActivityProfileEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile_edit)

        val userdetail = intent.getSerializableExtra("user") as User

       // Toast.makeText(this,"${user!!.firstName}",Toast.LENGTH_SHORT).show()
        with(binding){
           // editFirstName.text = userdetail?.firstName?.toEditable()

        }


    }

    fun String.toEditable() : Editable = Editable.Factory.getInstance().newEditable(this)
}