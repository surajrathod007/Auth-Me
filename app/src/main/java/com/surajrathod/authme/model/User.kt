package com.surajrathod.authme.model

data class User(
    val emailId : String,
    val firstName : String,
    val lastName : String,
    val mobileNo : String ="",
    val address : String ="",
    val token : String = "",
    val otp : String = ""
)
