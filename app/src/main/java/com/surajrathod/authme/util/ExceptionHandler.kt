package com.surajrathod.authme.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ExceptionHandler {
    fun catchOnLog(e:Exception){
        Log.d("catchOnLog", "$e")
    }
    fun catchOnContext(context : Context, e:Exception){
            Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
    }
}