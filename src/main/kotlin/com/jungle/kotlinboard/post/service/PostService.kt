package com.jungle.kotlinboard.post.service

import com.jungle.kotlinboard.post.domain.Post
import com.jungle.kotlinboard.post.domain.PostRepository
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional // DB ACID속성 유지하기 위함
class PostsService(
    private val postRepository: PostRepository,
) {
    // 게시글 하나 보여주기
    @Transactional(readOnly = true)
    fun getPost(postId:Long) : PostResponse {
        // orElseThrow : null이면 오류를 반환하라는 함수
        val post=postRepository.findById(postId).orElseThrow()

        return PostResponse(
            id = post.id,
            title = post.title,
            content = post.content,
            password = post.password,
            createdDate = post.createdDate,
            createdBy = post.createdBy,
        )
    }

    // 게시판 목록 보여주기
    @Transactional(readOnly = true)
    fun getPosts():List<Post> = postRepository.findAll()

    fun insertPost(postCreateRequest: PostCreateRequest) : PostResponse {
        val post : Post =
            postRepository.save(
                Post.of(
                    title = postCreateRequest.title,
                    content = postCreateRequest.content,
                    password= postCreateRequest.password))

        return PostResponse(
            id= post.id,
            title=post.title,
            content=post.content,
            password=post.password,
            createdDate = post.createdDate,
            createdBy = post.createdBy
        )
    }

    fun updatePost(
        id:Long,
        postUpdateRequest: PostUpdateRequest
    ) {
        val post = postRepository.findById(id).orElseThrow()

        if(!post.checkPassword(postUpdateRequest.password)){
            throw BadRequestException("비밀번호가 일치하지 않습니다.")
        }

        if(!postUpdateRequest.title.isNullOrBlank()){
            post.updateTitle(postUpdateRequest.title)
        }
        if(!postUpdateRequest.content.isNullOrBlank()){
            post.updateContent(postUpdateRequest.content)
        }
    }
    fun deletePost(id:Long, pw:String) {
        val post = postRepository.findById(id).orElseThrow()
        if(post.checkPassword(pw)){
            postRepository.delete(post)
        } else{
            throw IllegalArgumentException("패스워드가 일치하지 않습니다.")
        }
    }
}