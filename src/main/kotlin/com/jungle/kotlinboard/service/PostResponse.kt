package com.jungle.kotlinboard.service

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

data class PostResponse(
    val id:Long,
    val title:String,
    val content:String,
    val password:String,
    val createdDate: LocalDateTime,
    val createdBy:String,
)
