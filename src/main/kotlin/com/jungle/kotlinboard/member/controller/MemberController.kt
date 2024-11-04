package com.jungle.kotlinboard.member.controller

import com.jungle.kotlinboard.common.BaseResponse
import com.jungle.kotlinboard.common.authority.TokenInfo
import com.jungle.kotlinboard.member.domain.Member
import com.jungle.kotlinboard.member.dto.CustomUser
import com.jungle.kotlinboard.member.dto.LoginDto
import com.jungle.kotlinboard.member.dto.MemberDtoRequest
import com.jungle.kotlinboard.member.dto.MemberDtoResponse
import com.jungle.kotlinboard.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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
        @RequestBody
        @Valid
        loginDto: LoginDto
    ):BaseResponse<TokenInfo>{
        val tokenInfo = memberService.login(loginDto)
        return BaseResponse(data = tokenInfo)
    }
    @GetMapping("/info")
    fun searchMyInfo(): BaseResponse<MemberDtoResponse> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val response = memberService.searchMyInfo(userId)
        return BaseResponse(data = response)
    }

    /**
     * 내 정보 수정
     */
    @PutMapping("/info")
    fun saveMyInfo(@RequestBody @Valid memberDtoRequest: MemberDtoRequest):BaseResponse<Unit>{ //Unit : NULL로 설정하겠다.
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        memberDtoRequest.id = userId
        val resultMsg : String = memberService.saveMyInfo(memberDtoRequest)
        return BaseResponse(message= resultMsg)
    }

    // 회원 탈퇴
    @DeleteMapping("/info")
    fun deleteMyInfo(): BaseResponse<String> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        memberService.deleteUserById(userId)
        return BaseResponse(message = "회원 탈퇴가 완료되었습니다.")
    }


}