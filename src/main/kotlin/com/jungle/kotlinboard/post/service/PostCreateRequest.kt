package com.jungle.kotlinboard.post.service

// 자바의 record와 같은 개념 : data class
data class PostCreateRequest(
    val title: String,
    val content: String,
)
