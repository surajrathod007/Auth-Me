package com.surajrathod.authme.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {

    val networkInstance: NetworkInterface

    init {
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        networkInstance = retrofit.create(NetworkInterface::class.java)
    }

    //network checking
    fun checkForInternet(context : Context):Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo?=connectivityManager.activeNetworkInfo
        return if(activeNetwork?.isConnected!=null){
            activeNetwork.isConnected
        }else{
            false
        }
    }


}