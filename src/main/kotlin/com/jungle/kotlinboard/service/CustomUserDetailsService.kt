package com.jungle.kotlinboard.service

import com.jungle.kotlinboard.domain.Member
import com.jungle.kotlinboard.domain.MemberRepository
import com.jungle.kotlinboard.dto.CustomUser
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService (
    private val memberRepository : MemberRepository,
    private val passwordEncoder : PasswordEncoder,
) : UserDetailsService { // 로그인할때무조건드 지나가야하는 security
    override fun loadUserByUsername(username: String): UserDetails =
        memberRepository.findByUserName(username)
            ?.let{createUserDetails(it)} ?: throw UsernameNotFoundException("해당 유저는 없습니다.")

    private fun createUserDetails(member : Member) : UserDetails =
        CustomUser(
            member.id!!,
            member.userName,
            passwordEncoder.encode(member.password),
            member.memberRole!!.map{SimpleGrantedAuthority("ROLE_${it.role}")}
        )
}
