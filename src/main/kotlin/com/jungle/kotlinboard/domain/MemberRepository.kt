package com.jungle.kotlinboard.domain

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member,String> {
    fun findByUserName(userName:String) : Member?
}