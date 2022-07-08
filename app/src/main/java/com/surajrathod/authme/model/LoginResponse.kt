package com.surajrathod.authme.model

data class LoginResponse(
    val simpleResponse: SimpleResponse,
    val user: User
)
