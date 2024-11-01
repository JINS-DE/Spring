package com.jungle.kotlinboard.service

data class PostUpdateRequest(
    val title: String,
    val content: String,
    val password:String,
)
