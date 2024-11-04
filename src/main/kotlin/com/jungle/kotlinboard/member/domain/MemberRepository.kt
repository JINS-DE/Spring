package com.jungle.kotlinboard.member.domain

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member,Long> { // Member는 Entity 클래스 타입, Long은 PK 타입
    fun findByUserName(userName:String) : Member?
}

interface MemberRoleRepository : JpaRepository<MemberRole, Long> {
    fun deleteByMember(member: Member)
}