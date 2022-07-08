package com.surajrathod.authme.util

import android.R.id.message
import android.content.Context
import android.graphics.PorterDuff
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.surajrathod.authme.R
import retrofit2.Response.error


object ExceptionHandler {
    fun catchOnLog(e:String){
        Log.d("catchOnLog", e)
    }
    fun catchOnContext(context : Context, e: String){
        val toast = Toast.makeText(context, e, Toast.LENGTH_LONG)
        toast.show()

    }
    fun successToast(){

    }
}