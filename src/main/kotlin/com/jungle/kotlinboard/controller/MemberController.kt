package com.jungle.kotlinboard.controller

import com.jungle.kotlinboard.common.BaseResponse
import com.jungle.kotlinboard.domain.Member
import com.jungle.kotlinboard.domain.MemberRepository
import com.jungle.kotlinboard.dto.MemberDtoRequest
import com.jungle.kotlinboard.service.UserCreateRequest
import com.jungle.kotlinboard.service.UserLoginRequest
import com.jungle.kotlinboard.service.MemberService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/member")
class MemberController(
    private val memberService: MemberService,
//    private val memberRepository: MemberRepository
){
    @GetMapping("/list")
    fun userList():List<Member> = memberService.userList()

    // 회원가입
    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid memberDtoRequest: MemberDtoRequest):BaseResponse<Unit>{ //@Valid 체크 된 부분만 벨리데이션체크가능
        val resultMsg:String = memberService.signUp(memberDtoRequest)
        return BaseResponse(message = resultMsg)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody user: UserLoginRequest
    ):Map<String,Any>{
        return memberService.login(user)
    }



}