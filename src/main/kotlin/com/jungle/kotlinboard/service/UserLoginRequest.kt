package com.jungle.kotlinboard.service

import javax.print.attribute.standard.JobOriginatingUserName

data class UserLoginRequest(
    val userName: String,
    val password : String,
)
