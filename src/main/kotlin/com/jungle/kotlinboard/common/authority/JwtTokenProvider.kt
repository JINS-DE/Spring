package com.jungle.kotlinboard.common.authority

import com.jungle.kotlinboard.member.dto.CustomUser
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*


const val EXPIRATION_MILLISECONDS: Long = 1000* 60 *30

@Component // 스프링 컨테이너가 관리(객체들을 컴포넌트로)
class JwtTokenProvider {
    @Value("\${jwt.secret}") // yaml에서 jwt의 secret키를 가져옴.
    lateinit var secretKey:String // 비밀키 초기화 지연

    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))} //BASE64 디코딩

    /**
     * Token  생성
     */
    fun createToken(authentication: Authentication): TokenInfo{
        val authorities:String = authentication
            .authorities
            .joinToString(separator = ",", transform = GrantedAuthority::getAuthority)
        val now = Date()
        val accessExpiration = Date(now.time + EXPIRATION_MILLISECONDS) // 유효시간 설정

        // payload 사용자 정보 저장
        val accessToken = Jwts
            .builder()
            .setSubject(authentication.name) //payload, userId 넣어줌, 토큰 소유자 확인
            .claim("auth",authorities) //payload
            .claim("userId",(authentication.principal as CustomUser).userId)
            .setIssuedAt(now) //현재발행시간
            .setExpiration(accessExpiration) // 언제까지 유효한지
            .signWith(key, SignatureAlgorithm.HS256) //시그니처 어떤알고리즘 썼는지
            .compact() //압축
        return TokenInfo("Bearer", accessToken)

    }

    /**
     * Token 정보 추출
     * 토큰 확인 (사용자가 누군지확인하는함수)
     */
    fun getAuthentication(token:String) : Authentication {
        val claims : Claims = getClaims(token)
        val auth = claims["auth"] ?: throw RuntimeException("잘못된 토큰입니다.")
        val userId = claims["userId"] ?: throw RuntimeException("잘못된 토큰입니다.")
        // 권한 정보 추출
        val authorities: Collection<GrantedAuthority> = (auth as String)
            .split(",")
            .map { SimpleGrantedAuthority(it) }

        val principal : UserDetails = CustomUser(userId.toString().toLong(), claims.subject, "",authorities)
        return UsernamePasswordAuthenticationToken(principal,"", authorities)
    }

    /**
     * Token 검증
     */
    fun validateToken(token: String) : Boolean {
        try{
            getClaims(token)
            return true
        } catch (e:Exception){
            when(e){
                is SecurityException -> {}
                is MalformedJwtException -> {}
                is ExpiredJwtException -> {}
                is UnsupportedJwtException -> {}
                is IllegalArgumentException -> {}
                else -> {}
            }
            println(e.message)
        }
        return false
    }

    // payload 값 가져오기
    private fun getClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

}