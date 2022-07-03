package com.surajrathod.authme.util

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.surajrathod.authme.R

class GenericTextWatcher(view: View, private val editText: Array<EditText>) :
    TextWatcher {
    private val view: View
    override fun afterTextChanged(editable: Editable) {
        val text = editable.toString()
        when (view.getId()) {
            R.id.etOtp1 -> if (text.length == 1) editText[1].requestFocus()
            R.id.etOtp2 -> if (text.length == 1) editText[2].requestFocus() else if (text.length == 0) editText[0].requestFocus()
            R.id.etOtp3 -> if (text.length == 1) editText[3].requestFocus() else if (text.length == 0) editText[1].requestFocus()
            R.id.etOtp4 -> if (text.length == 0) editText[2].requestFocus()
        }
    }

    override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
    override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}

    init {
        this.view = view
    }
}