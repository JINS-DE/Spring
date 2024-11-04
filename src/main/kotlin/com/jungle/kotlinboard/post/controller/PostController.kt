package com.jungle.kotlinboard.post.controller

import com.jungle.kotlinboard.post.domain.Post
import com.jungle.kotlinboard.post.service.PostCreateRequest
import com.jungle.kotlinboard.post.service.PostResponse
import com.jungle.kotlinboard.post.service.PostUpdateRequest
import com.jungle.kotlinboard.post.service.PostsService
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@RestController // Controller를 RestFul하게 관리
@RequestMapping("/api/posts")
class PostController (
    private val postService : PostsService
){
    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable postId : Long,
    ): PostResponse = postService.getPost(postId)

    @GetMapping
    fun getPosts() : List<Post> = postService.getPosts()

    @PatchMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody postUpdateRequest: PostUpdateRequest,
    ){
        postService.updatePost(postId, postUpdateRequest)
    }
    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable postId: Long,
    ){
        postService.deletePost(postId)
    }

    @PostMapping()
    fun createPost(
        // 리퀘스트 파람은 짧은 데이터일 때, 바디는 긴내용을 객체로 받을 때
        @RequestBody postCreateRequest: PostCreateRequest
    ): PostResponse = postService.insertPost(postCreateRequest)
}