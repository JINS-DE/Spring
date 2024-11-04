package com.jungle.kotlinboard.post.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "posts"
)
// 코틀린은 ()안에 넣어주면 생성자가 자동으로 생성 , 자바는 {}안에넣음!
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Long = 0L,

    @Column(nullable = false, length = 255)
    var title:String,
    @Column(nullable = false, length = 6553)
    var content:String,
    @Column(nullable = false, updatable = false, length = 5)
    val password:String,
    @Column(nullable = false, updatable = false)
    val createdDate:LocalDateTime,
    @Column(nullable = false, updatable = false)
    val createdBy:String,
) {
    // public static return 메서드명() 이거를 companion으로 코틀린에서 사용 가능
    companion object{
        fun of(
            title: String,
            content: String,
            password: String):
                Post = Post(title = title, content = content, password = password, createdDate = LocalDateTime.now(), createdBy = "test",)
    }

    fun updateTitle(title:String){
        this.title = title
    }
    fun updateContent(content:String){
        this.content = content
    }

    fun checkPassword(password: String) = this.password == password
}