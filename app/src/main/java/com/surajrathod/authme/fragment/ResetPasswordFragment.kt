package com.surajrathod.authme.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
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
           }


         edit =
            arrayOf<EditText>(otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four)

        otp_textbox_one.addTextChangedListener(GenericTextWatcher(otp_textbox_one, edit))
        otp_textbox_two.addTextChangedListener(GenericTextWatcher(otp_textbox_two, edit))
        otp_textbox_three.addTextChangedListener(GenericTextWatcher(otp_textbox_three, edit))
        otp_textbox_four.addTextChangedListener(GenericTextWatcher(otp_textbox_four, edit))

        val password = binding.etPassword
        val confirmPassword = binding.etConfirmPassword

        verify_otp.setOnClickListener {
            if(!isDataFillled(password))else if(!isDataFillled(confirmPassword))else{
                if(isEnteredOtp()){
                    email = arguments?.get("email") as String?
                    Toast.makeText(activity, "$email", Toast.LENGTH_SHORT).show()
                    d.toggleDialog(dd)
                    verifyAndResetPassword(confirmPassword)
                }else{
                    Snackbar.make(view, "Please Enter OTP", 1000).show()
                }
            }
        }
        return view
    }
    fun isDataFillled(view: TextView) : Boolean{
        if (TextUtils.isEmpty(view.text.toString().trim() { it <= ' ' })) {
            Snackbar.make(view, "Fill up Fields", 1000).show()
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
        Toast.makeText(activity, "$otp", Toast.LENGTH_SHORT).show()
        return true
    }
    fun verifyAndResetPassword(confirmPassword : EditText){
        lifecycleScope.launch{
            try {
                val newpass = binding.etConfirmPassword.text.toString()
                val response = NetworkService.networkInstance.resetPassword(email!!,otp!!,newpass)
                onSimpleResponse("Reset",response)
            }catch (e : Exception){
                activity?.let { ExceptionHandler.catchOnContext(it,e) }
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
            Toast.makeText(activity, simpleResponse.message, Toast.LENGTH_SHORT).show()
        }
    }
}