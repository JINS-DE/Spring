package com.jungle.kotlinboard.common.authority

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean

// spring security에서 자동으로 사용, 클라이언트와 컨트롤러 사이에 filter가 있음.
// authorization ,bearer, jwt
class JwtAuthenticationFilter (
    private val jwtTokenProvider : JwtTokenProvider
): GenericFilterBean() {
    // jwt 토큰이 맞는지 확인 후 가져옴
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val token = resolveToken(request as HttpServletRequest)
        if (token != null && jwtTokenProvider.validateToken(token)) {
            val authentication = jwtTokenProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }
        chain?.doFilter(request, response)
    }

    // substring 과정
    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer")){
            bearerToken.substring(7)
        }else{
            null
        }
    }

}