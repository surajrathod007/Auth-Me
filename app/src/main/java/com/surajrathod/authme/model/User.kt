package com.surajrathod.authme.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


@Entity(tableName = "user_table")
//@Parcelize
data class User(
    @PrimaryKey
    val emailId : String,
    val firstName : String,
    val lastName : String,
    val mobileNo : String ="",
    val address : String ="",
    val token : String? = "",
    val otp : String = ""
) : Serializable
