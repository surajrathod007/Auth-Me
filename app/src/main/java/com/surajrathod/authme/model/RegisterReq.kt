package com.surajrathod.authme.model

data class RegisterReq(
    val firstName : String,
    val lastName : String,
    val emailId : String,
    val password : String
)
