package com.jungle.kotlinboard.domain

import jakarta.persistence.*

@Entity
@Table(
    uniqueConstraints =  [UniqueConstraint(name = "uk_member_user_name", columnNames = ["user_name"])],
    name = "members",
)
class Member (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long?=null,

    @Column(nullable = false, unique = true)
    var userName: String,

    @Column(nullable = false)
    val password:String,

    @Column(nullable = false)
    var nickName:String,
) {
    companion object {
        fun of(
            userName: String,
            password: String,
            nickName: String):
            Member = Member(userName=userName, password = password, nickName = nickName)
    }
}
