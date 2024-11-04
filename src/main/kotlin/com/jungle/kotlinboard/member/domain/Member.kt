package com.jungle.kotlinboard.member.domain

import com.jungle.kotlinboard.common.status.ROLE
import com.jungle.kotlinboard.member.dto.MemberDtoResponse
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
)
{
    // fetch : 한꺼번에 가져올지 레이지로딩할지, 레이지로딩이 국룰
    // 레이지 로딩 :
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "member")
    val memberRole: List<MemberRole>? = null

    fun toDto() : MemberDtoResponse =
        MemberDtoResponse(id!!,userName,nickName)

//    companion object {
//        fun of(
//            userName: String,
//            password: String,
//            nickName: String):
//            Member = Member(userName=userName, password = password, nickName = nickName)
//    }

}

@Entity
class MemberRole(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long?=null,

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    var role: ROLE,

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name="fk_member_role_member_id"))
    val member: Member,
)

