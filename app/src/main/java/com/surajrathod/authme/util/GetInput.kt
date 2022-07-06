package com.surajrathod.authme.util

import android.widget.EditText

object GetInput {
    fun takeFrom(view : EditText):String{
        return view.text.toString()
    }


}