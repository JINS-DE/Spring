package com.jungle.kotlinboard.common.authority

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.server.WebFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val jwtTokenProvider : JwtTokenProvider
){
    @Bean // return값을 Bean으로 올리고 싶을 때 , http 인증 방식을 커스텀하는 방식 현재
    fun filterChain(http: HttpSecurity) : SecurityFilterChain {
        http
            .httpBasic { it.disable() }
            .csrf { it.disable() } // rest api 를 사용해서 사용안함
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // 세션 안사용함 토큰사용함
            .authorizeHttpRequests {
                it.requestMatchers("/api/member/signup", "/api/member/login").anonymous()
                    .requestMatchers("/api/member/info/**").hasRole("MEMBER")
                    .requestMatchers("/api/posts/**").hasRole("MEMBER")
                    .anyRequest().permitAll()
            }.addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        PasswordEncoderFactories.createDelegatingPasswordEncoder()

}


