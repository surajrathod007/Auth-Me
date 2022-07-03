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
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.surajrathod.authme.R
import com.surajrathod.authme.databinding.FragRegisterBinding
import com.surajrathod.authme.model.RegisterReq
import com.surajrathod.authme.model.SimpleResponse
import com.surajrathod.authme.network.NetworkService
import com.surajrathod.authme.util.ExceptionHandler
import com.surajrathod.authme.util.LoadingScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFrag : Fragment() {

    lateinit var binding: FragRegisterBinding
    lateinit var d : LoadingScreen
    lateinit var dd : Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.frag_register, container, false)
        binding = FragRegisterBinding.bind(view)
        d = LoadingScreen(activity as Context)
        dd = d.loadingScreen()
        binding.tvLoginHere.setOnClickListener {
            findNavController().navigate(R.id.action_registerFrag_to_loginFrag)
        }
        binding.btnRegister.setOnClickListener {
            val firstName = binding.ETFirstName
            val lastName = binding.ETLastName
            val email = binding.ETEmail
            val password = binding.ETPassword
            if(!isDataFillled(firstName))else if(!isDataFillled(lastName))else if(!isDataFillled(email))else if(!isDataFillled(password))else{
                registeruser(firstName.text.toString(),lastName.text.toString(),email.text.toString(),password.text.toString())
            }
        }

        return view
    }
    fun isDataFillled(view: TextView) : Boolean{
        if (TextUtils.isEmpty(view.text.toString().trim() { it <= ' ' })) {
            when(view){
                binding.ETFirstName -> Snackbar.make(view, "First Name is required", 1000).show()
                binding.ETLastName -> Snackbar.make(view, "Last Name is required", 1000).show()
                binding.ETEmail -> Snackbar.make(view, "Email is required", 1000).show()
                binding.ETPassword -> Snackbar.make(view, "Password is required", 1000).show()
            }

            return false
        }
        return true
    }
    fun registeruser(firstName : String,lastName : String,email : String,password : String){
        d.toggleDialog(dd)  // show
       lifecycleScope.launch {
           try{
               val register =NetworkService.networkInstance.registerUser(RegisterReq(firstName,lastName,email,password))
               onRegistrationStatus(register)
           }catch (e : Exception){
               activity?.let { ExceptionHandler.catchOnContext(it, e) }
               d.toggleDialog(dd)
           }
       }
    }
    fun onRegistrationStatus(simpleResponse: SimpleResponse ){
        if(simpleResponse.success){
            d.toggleDialog(dd)  // hide
            Toast.makeText(activity, "Registration Successful", Toast.LENGTH_SHORT).show()
        }else{
            d.toggleDialog(dd)  // hide
            Toast.makeText(activity, simpleResponse.message, Toast.LENGTH_SHORT).show()
        }
    }
}

