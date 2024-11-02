package com.jungle.kotlinboard.domain

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member,Long> {
    fun findByUserName(userName:String) : Member?
}

interface MemberRoleRepository : JpaRepository<MemberRole, Long> {
    fun deleteByMember(member: Member)
}