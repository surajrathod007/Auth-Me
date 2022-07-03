package com.surajrathod.authme.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.surajrathod.authme.R
import com.surajrathod.authme.databinding.FragLoginBinding
import com.surajrathod.authme.util.LoadingScreen

class LoginFrag : Fragment() {
    lateinit var binding : FragLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.frag_login, container, false)
        binding = FragLoginBinding.bind(view)
        val email = binding.ETEmail
        val password = binding.ETPassword
        with(binding){
            TVRegister.setOnClickListener { findNavController().navigate(R.id.action_loginFrag_to_registerFrag) }
            TVForgotPassword.setOnClickListener { findNavController().navigate(R.id.action_loginFrag_to_forgotPasswordFragment) }
            BtnLogin.setOnClickListener {
                Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
                 if(!isDataFillled(email))else if(!isDataFillled(password))else{
                    registeruser(email.text.toString(),password.text.toString())
                }
            }
        }
        return view
    }
    fun isDataFillled(view: TextView) : Boolean{
        if (TextUtils.isEmpty(view.text.toString().trim() { it <= ' ' })) {
            Snackbar.make(view, view.hint.toString() + " is required", 1000).show()
            return false
        }
        return true
    }
    fun registeruser(email : String,password : String){
        val d = LoadingScreen(activity as Context)
        val dd = d.loadingScreen()
        d.toggleDialog(dd) //show

    }

}