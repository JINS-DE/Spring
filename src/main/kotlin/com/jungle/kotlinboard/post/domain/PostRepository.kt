package com.jungle.kotlinboard.post.domain

import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByOrderByCreatedDateDesc(): List<Post>
}