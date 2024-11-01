package com.jungle.kotlinboard.service

data class UserCreateRequest(
    val userName : String,
    val password : String,
    val nickName : String,
)
