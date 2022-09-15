package com.surajrathod.authme.fragment

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.surajrathod.authme.R
import com.surajrathod.authme.databinding.FragmentResetPasswordBinding
import com.surajrathod.authme.model.SimpleResponse
import com.surajrathod.authme.network.NetworkService
import com.surajrathod.authme.util.ExceptionHandler
import com.surajrathod.authme.util.GenericTextWatcher
import com.surajrathod.authme.util.GetInput
import com.surajrathod.authme.util.LoadingScreen
import kotlinx.coroutines.launch

class ResetPasswordFragment : Fragment() {
    lateinit var otp_textbox_one : EditText
    lateinit var otp_textbox_two : EditText
    lateinit var otp_textbox_three:EditText
    lateinit var otp_textbox_four:EditText
    lateinit var verify_otp: Button
    lateinit var binding : FragmentResetPasswordBinding
    lateinit var edit : Array<EditText>
    lateinit var password : EditText
    lateinit var confirmPassword : EditText

    lateinit var d : LoadingScreen
    lateinit var dd : Dialog

    var email : String? = null
    var otp : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_reset_password, container, false)
        binding = FragmentResetPasswordBinding.bind(view)
        d = LoadingScreen(activity as Context)
        dd = d.loadingScreen()

           with(view){
               otp_textbox_one = findViewById(R.id.etOtp1)
               otp_textbox_two = findViewById(R.id.etOtp2)
               otp_textbox_three = findViewById(R.id.etOtp3)
               otp_textbox_four = findViewById(R.id.etOtp4)
               verify_otp = findViewById(R.id.btnReset)
               password = findViewById(R.id.etPassword)
               confirmPassword = findViewById(R.id.etConfirmPassword)
           }


         edit =
            arrayOf<EditText>(otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four)

        otp_textbox_one.addTextChangedListener(GenericTextWatcher(otp_textbox_one, edit))
        otp_textbox_two.addTextChangedListener(GenericTextWatcher(otp_textbox_two, edit))
        otp_textbox_three.addTextChangedListener(GenericTextWatcher(otp_textbox_three, edit))
        otp_textbox_four.addTextChangedListener(GenericTextWatcher(otp_textbox_four, edit))


        verify_otp.setOnClickListener {
            if(!isDataFillled(password))else if(!isDataFillled(confirmPassword))else{
                Log.d(TAG, "${password.text},${confirmPassword.text}")
                if(password.text.toString()!=confirmPassword.text.toString()){
                    ExceptionHandler.catchOnContext(activity!!,"Recheck Both Password")
                    return@setOnClickListener
                }
            }
            if(isEnteredOtp()){
                email = arguments?.get("email") as String?
                d.toggleDialog(dd)
                verifyAndResetPassword()
            }else{
                Snackbar.make(view, "Please Enter OTP", 1000).show()
            }
        }
        return view
    }
    fun isDataFillled(view: TextView) : Boolean{
        if (TextUtils.isEmpty(view.text.toString().trim() { it <= ' ' })) {
            Snackbar.make(view, "Fields are empty", 1000).show()
            return false
        }
        return true
    }
    fun isEnteredOtp() : Boolean{
        if(otp_textbox_one.text.isEmpty()) return false
        if(otp_textbox_two.text.isEmpty()) return false
        if(otp_textbox_three.text.isEmpty()) return false
        if(otp_textbox_four.text.isEmpty()) return false
        otp=""
       edit.forEach {
           otp+=GetInput.takeFrom(it)
       }
//        Toast.makeText(activity, "$otp", Toast.LENGTH_SHORT).show()
        return true
    }
    fun verifyAndResetPassword(){
        lifecycleScope.launch{
            try {
                val newpass = confirmPassword.text.toString()
//                Log.d(TAG, "$email $otp $newpass")
                val ot = requireArguments().get("otp") as String ?: ""

                if(ot==otp){
                    val response = NetworkService.networkInstance.resetPassword(email!!,otp!!,newpass)
                    onSimpleResponse("Reset",response)
                }else{
                    activity?.let{
                        ExceptionHandler.catchOnContext(it.applicationContext,"Wrong otp")
                    }
                }

            }catch (e : Exception){
                d.toggleDialog(dd)
                activity?.let {
                    activity?.let { ExceptionHandler.catchOnContext(it,"Wrong OTP") }
                }
            }
        }
    }
    fun onSimpleResponse(task : String,simpleResponse: SimpleResponse){
        if(simpleResponse.success){
            d.toggleDialog(dd)  // hide
            Toast.makeText(activity, "$task Successful", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFrag)
        }else{
            d.toggleDialog(dd)  // hide
            activity?.let { ExceptionHandler.catchOnContext(it,simpleResponse.message) }
        }
    }
}