package com.surajrathod.authme

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.datastore.preferences.core.edit
import com.surajrathod.authme.database.UserDatabase
import com.surajrathod.authme.databinding.ActivityProfileBinding
import com.surajrathod.authme.fragment.LoginFrag
import com.surajrathod.authme.model.User
import com.surajrathod.authme.util.DataStore
import com.surajrathod.authme.util.DataStore.preferenceDataStoreAuth
import kotlinx.coroutines.*

class ProfileActivity : AppCompatActivity() {

    lateinit var btnEdit : Button
    lateinit var btnLogout : Button


    lateinit var txtUserName : TextView
    lateinit var txtEmail : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_profile)

        txtUserName = findViewById(R.id.tvDisplayName)
        txtEmail = findViewById(R.id.tvUserName)
        btnLogout = findViewById(R.id.btnLogout)

        val sharedPreferences = getSharedPreferences("user_e", Context.MODE_PRIVATE)
        val e = sharedPreferences.getString("email","no email")
        val token = intent.getStringExtra(DataStore.JWT_TOKEN)
        //val email = intent.getStringExtra("email")

        //Toast.makeText(this,"Hii ${email} ",Toast.LENGTH_SHORT).show()
        val db = UserDatabase.getDatabase(this).userDao()

        val user = db.getUser(e!!)

        user.observe(this,{
            txtUserName.text = it.firstName
            txtEmail.text = it.emailId
        })
        btnEdit = findViewById(R.id.btnEditProfile)
        btnEdit.setOnClickListener {
            val intent = Intent(this@ProfileActivity,ProfileEdit::class.java)
            //intent.putExtra("user",user)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {

            try{

            GlobalScope.launch(Dispatchers.IO){
                preferenceDataStoreAuth.edit {
                    it.clear()
                }
            }

                Toast.makeText(this.applicationContext,"Logged Out !",Toast.LENGTH_SHORT).show()

                finish()




            }catch (e : Exception){
                Toast.makeText(this.applicationContext,e.message,Toast.LENGTH_SHORT).show()
            }
        }

    }




}