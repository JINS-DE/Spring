//package com.jungle.kotlinboard.service
//
//import com.jungle.kotlinboard.post.domain.Post
//import com.jungle.kotlinboard.post.domain.PostRepository
//import com.jungle.kotlinboard.post.service.PostCreateRequest
//import com.jungle.kotlinboard.post.service.PostUpdateRequest
//import com.jungle.kotlinboard.post.service.PostsService
//import org.apache.coyote.BadRequestException
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertThrows
//import org.mockito.ArgumentMatchers.any
//import org.mockito.Mockito.*
//import java.time.LocalDateTime
//import java.util.*
//
//class PostsServiceTest {
//    private lateinit var postsService: PostsService
//    private lateinit var postRepository: PostRepository
//
//    // setup -> 테스트 케이스 (단일 케이스)
//    @BeforeEach // 각 테스트가 실행되기 전 초기화
//    fun setup(){
//        postRepository = mock()
//        postsService = PostsService(postRepository)
//    }
//
//    @Test
//    fun `게시글 생성 테스트 성공`(){
//        // given : 어떤 조건으로 테스트할건지
//        val postCreateRequest: PostCreateRequest = PostCreateRequest(
//            title = "test title",
//            content = "test content",
//            password = "1234"
//        )
//        val post =
//            Post.of(
//                title = "test title",
//                content = "test content",
//                password= "1234",)
//
//        // when : save 될 때의 시점!
//        // any(Post..) : Post타입 객체 모두 허용
//        // thenReturn : post에 리턴
//        `when`(postRepository.save(any(Post::class.java))).thenReturn(post)
//
//        // when : 테스트 하고싶은코드가 발생했을때
//        val result = postsService.insertPost(postCreateRequest)
//
//        // then : 결과가 이렇게 나타난다.
//        assertEquals(result.title, postCreateRequest.title)
//        assertEquals(result.content, postCreateRequest.content)
//        assertEquals(result.password, postCreateRequest.password)
//
//
//    }
//
//    @Test
//    fun `단일 게시글 가져오기 테스트 성공`() {
//        //given
//        val postId:Long = 0L
//        val post = Post(
//            id = 0L,
//            title = "test title",
//            content = "test content",
//            password = "1234",
//            createdDate = LocalDateTime.now(),
//            createdBy = "test"
//        )
//
//        `when`(postRepository.findById(postId)).thenReturn((Optional.of(post)))
//
//        //when
//        val result = postsService.getPost(postId)
//
//        //then
//        assertEquals(result.id, post.id)
//        assertEquals(result.title, post.title)
//        assertEquals(result.content, post.content)
//        assertEquals(result.password, post.password)
//        assertEquals(result.createdDate, post.createdDate)
//        assertEquals(result.createdBy, post.createdBy)
//    }
//
//    @Test
//    fun `전체 게시글 가져오기 테스트 성공`(){
//        //given
//        val posts = mutableListOf<Post>()
//
//        for (i in 1..3) {
//            val post = Post(
//                id = i.toLong(),
//                title = "Title $i",
//                content = "내용 $i",
//                password = "12345",
//                createdDate = LocalDateTime.now(),
//                createdBy = "test$i"
//            )
//            posts.add(post)
//        }
//
//        `when`(postRepository.findAll()).thenReturn((posts))
//
//        //when
//        val result = postsService.getPosts()
//
//        //then
//        assertEquals(posts.size,result.size)
//    }
//
//    @Test
//    fun `게시글 삭제 성공`(){
//        //given
//        val postId : Long = 0L
//        val password : String = "1234"
//
//        val post = Post(
//            id = 0L,
//            title = "test title",
//            content = "test content",
//            password = "1234",
//            createdDate = LocalDateTime.now(),
//            createdBy = "test"
//        )
//
//        `when`(postRepository.findById(postId)).thenReturn(Optional.of(post))
//
//        postsService.deletePost(postId, password)
//
//        verify(postRepository).delete(post)
//    }
//
//    @Test
//    fun `게시글 수정 성공`(){
//        //given
//        val postId = 0L
//
//        val postUpdateRequest = PostUpdateRequest(
//            title = "title2",
//            content = "test2 content",
//            password = "1234",
//        )
//
//        val post = Post(
//            id = 0L,
//            title = "test title",
//            content = "test content",
//            password = "1234",
//            createdDate = LocalDateTime.now(),
//            createdBy = "test"
//        )
//
//        `when`(postRepository.findById(postId)).thenReturn((Optional.of(post)))
//
//        postsService.updatePost(postId,postUpdateRequest)
//
//        assertEquals(post.title,postUpdateRequest.title)
//        assertEquals(post.content,postUpdateRequest.content)
//    }
//
//    @Test
//    fun `게시글 수정 실패`(){
//        //given
//        val postId = 0L
//
//        val postUpdateRequest = PostUpdateRequest(
//            title = "title2",
//            content = "test2 content",
//            password = "12345",
//        )
//
//        val post = Post(
//            id = 0L,
//            title = "test title",
//            content = "test content",
//            password = "1234",
//            createdDate = LocalDateTime.now(),
//            createdBy = "test"
//        )
//
//        `when`(postRepository.findById(postId)).thenReturn((Optional.of(post)))
//        assertThrows<BadRequestException>{
//            postsService.updatePost(postId,postUpdateRequest)
//        }
//
//    }
//}