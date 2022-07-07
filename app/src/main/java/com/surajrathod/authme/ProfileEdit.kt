package com.surajrathod.authme

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.surajrathod.authme.database.UserDatabase
import com.surajrathod.authme.databinding.ActivityProfileEditBinding
import com.surajrathod.authme.model.User

class ProfileEdit : AppCompatActivity() {


    lateinit var binding: ActivityProfileEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile_edit)


        val sharedPreferences = getSharedPreferences("user_e", Context.MODE_PRIVATE)
        val e = sharedPreferences.getString("email","no email")

        val db = UserDatabase.getDatabase(this).userDao()

        val user = db.getUser(e!!)
        user.observe(this,{

            binding.editFirstName.text = it.firstName.toEditable()
            binding.editLastName.text = it.lastName.toEditable()
            binding.editAddress.text = it.address.toEditable()
            binding.editPhone.text = it.mobileNo.toEditable()
            binding.editEmailName.text = it.emailId.toEditable()
        })


    }

    fun String.toEditable() : Editable = Editable.Factory.getInstance().newEditable(this)
}