package com.surajrathod.authme.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


//@Parcelize
data class User(
    val emailId : String,
    val firstName : String,
    val lastName : String,
    val mobileNo : String ="",
    val address : String ="",
    val token : String? = "",
    val otp : String? = ""
) : Serializable
