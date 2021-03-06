package com.surajrathod.authme.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.surajrathod.authme.ProfileActivity
import com.surajrathod.authme.R
import com.surajrathod.authme.database.UserDatabase
import com.surajrathod.authme.database.UserEntity
import com.surajrathod.authme.databinding.FragLoginBinding
import com.surajrathod.authme.model.LoginReq
import com.surajrathod.authme.model.LoginResponse
import com.surajrathod.authme.model.User
import com.surajrathod.authme.network.NetworkService
import com.surajrathod.authme.util.DataStore
import com.surajrathod.authme.util.DataStore.preferenceDataStoreAuth
import com.surajrathod.authme.util.ExceptionHandler
import com.surajrathod.authme.util.LoadingScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFrag : Fragment() {
    lateinit var binding : FragLoginBinding
    lateinit var d : LoadingScreen
    lateinit var dd : Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.frag_login, container, false)
        binding = FragLoginBinding.bind(view)
        d = LoadingScreen(activity as Context)
        dd = d.loadingScreen()
        val email = binding.ETEmail
        val password = binding.ETPassword
        with(binding){
            TVRegister.setOnClickListener { findNavController().navigate(R.id.action_loginFrag_to_registerFrag) }
            TVForgotPassword.setOnClickListener { findNavController().navigate(R.id.action_loginFrag_to_forgotPasswordFragment) }
            BtnLogin.setOnClickListener {
                 if(!isDataFillled(email))else if(!isDataFillled(password))else{
                    loginUser(email.text.toString(),password.text.toString())
                }
            }
        }
        return view
    }
    fun isDataFillled(view: TextView) : Boolean{
        if (TextUtils.isEmpty(view.text.toString().trim() { it <= ' ' })) {
            when(view){
                binding.ETEmail -> Snackbar.make(view, "Email is required", 1000).show()
                binding.ETPassword -> Snackbar.make(view, "Password is required", 1000).show()
            }
            return false
        }
        return true
    }
    fun loginUser(email : String,password : String){
        d.toggleDialog(dd) //show

       lifecycleScope.launch {
           var response : LoginResponse? = null
           try {
                response =  NetworkService.networkInstance.loginUser(LoginReq(email,password))
              }catch (e:Exception){
               activity?.let { ExceptionHandler.catchOnContext(it, getString(R.string.generalErrorMsg)) }
               d.toggleDialog(dd)
              }
           if(response!=null){
               if(response.simpleResponse.success){
                   onSimpleResponse("Login",response.user)
                   Toast.makeText(activity, response.simpleResponse.message, Toast.LENGTH_SHORT).show()
               }else{
                   activity?.let { ExceptionHandler.catchOnContext(it, response.simpleResponse.message) }
                   d.toggleDialog(dd)
               }
           }
       }
    }
    fun onSimpleResponse(task : String, user: User){
        if(user.token!=null){
            d.toggleDialog(dd)  // hide
            lifecycleScope.launch{
                storeStringPreferences(DataStore.JWT_TOKEN,user.token)
            }

            val sharedPreference =  requireActivity().getSharedPreferences("user_e",Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            editor.putString("email",user.emailId)
            editor.commit()

            val intent = Intent(requireActivity(), ProfileActivity::class.java)

            val u = UserEntity(
                emailId = user.emailId,
                firstName = user.firstName,
                lastName = user.lastName,
                mobileNo = user.mobileNo,
                address = user.address,
                token = user.token,
                otp = "hi"
            )
            val db = UserDatabase.getDatabase(this.requireContext())
            GlobalScope.launch(Dispatchers.IO) {
                db.userDao().insertUser(u)
            }

            startActivity(intent)
            activity?.finish()

        }else{
            d.toggleDialog(dd)  // hide
            Toast.makeText(activity, "$task Failed", Toast.LENGTH_SHORT).show()
        }
    }
    suspend fun storeStringPreferences(key: String ,value : String){
        activity?.let{ dsContext ->
            dsContext.preferenceDataStoreAuth.edit {
                it[stringPreferencesKey(key)] = value
            }
        }
    }
}