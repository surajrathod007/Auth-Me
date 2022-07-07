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
import com.surajrathod.authme.database.UserEntity
import com.surajrathod.authme.databinding.ActivityProfileEditBinding
import com.surajrathod.authme.model.User
import com.surajrathod.authme.network.NetworkService
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

        binding.btnUpdateProfile.setOnClickListener {
            val user = UserEntity(
                emailId = editEmailName.text.toString(),
                firstName = editFirstName.text.toString(),
                lastName = editLastName.text.toString(),
                mobileNo = editPhone.text.toString(),
                address = editAddress.text.toString(),
                token = "",
                otp = ""
            )

            try{

                GlobalScope.launch(Dispatchers.IO) {
                    val res = NetworkService.networkInstance.updateUser(User(
                        emailId = editEmailName.text.toString(),
                        firstName = editFirstName.text.toString(),
                        lastName = editLastName.text.toString(),
                        mobileNo = editPhone.text.toString(),
                        address = editAddress.text.toString(),
                        token = "",
                        otp = ""
                    ))
                    if(res.success){
                        db.updateUser(user)
                    }
                }


                Toast.makeText(this.applicationContext,"Profile Updated Succesfully",Toast.LENGTH_SHORT).show()
            }catch (e : Exception){
                Toast.makeText(this.applicationContext,e.message,Toast.LENGTH_SHORT).show()
            }

        }



    }

    fun String.toEditable() : Editable = Editable.Factory.getInstance().newEditable(this)
}