package com.surajrathod.authme.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.surajrathod.authme.R
import com.surajrathod.authme.databinding.FragLoginBinding
import com.surajrathod.authme.model.LoginReq
import com.surajrathod.authme.model.User
import com.surajrathod.authme.network.NetworkService
import com.surajrathod.authme.util.DataStore
import com.surajrathod.authme.util.DataStore.preferenceDataStoreAuth
import com.surajrathod.authme.util.ExceptionHandler
import com.surajrathod.authme.util.LoadingScreen
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
           try {
               val user = NetworkService.networkInstance.loginUser(LoginReq(email,password))
               onSimpleResponse("Login",user)
           }catch (e : Exception){
               activity?.let { ExceptionHandler.catchOnContext(it, e) }
               d.toggleDialog(dd)
           }
       }
    }
    fun onSimpleResponse(task : String, user: User){
        if(user.token!=null){
            d.toggleDialog(dd)  // hide
            lifecycleScope.launch{
                storeStringPreferences(DataStore.JWT_TOKEN,user.token)
            }
            Toast.makeText(activity, "$task Successful", Toast.LENGTH_SHORT).show()
            // TODO : Navigation to ProfileActivity / DashboardActivity
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