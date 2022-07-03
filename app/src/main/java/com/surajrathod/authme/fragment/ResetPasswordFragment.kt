package com.surajrathod.authme.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.surajrathod.authme.R
import com.surajrathod.authme.util.GenericTextWatcher

class ResetPasswordFragment : Fragment() {
    lateinit var otp_textbox_one : EditText
    lateinit var otp_textbox_two : EditText
    lateinit var otp_textbox_three:EditText
    lateinit var otp_textbox_four:EditText
    var verify_otp: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_reset_password, container, false)

           with(view){
               otp_textbox_one = findViewById(R.id.etOtp1)
               otp_textbox_two = findViewById(R.id.etOtp2)
               otp_textbox_three = findViewById(R.id.etOtp3)
               otp_textbox_four = findViewById(R.id.etOtp4)
               verify_otp = findViewById(R.id.verify_otp_btn)
           }


        val edit =
            arrayOf<EditText>(otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four)

        otp_textbox_one.addTextChangedListener(GenericTextWatcher(otp_textbox_one, edit))
        otp_textbox_two.addTextChangedListener(GenericTextWatcher(otp_textbox_two, edit))
        otp_textbox_three.addTextChangedListener(GenericTextWatcher(otp_textbox_three, edit))
        otp_textbox_four.addTextChangedListener(GenericTextWatcher(otp_textbox_four, edit))
        return view
    }
}