package com.jungle.kotlinboard.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.jungle.kotlinboard.domain.Member
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

// 다양한 용도로 사용하기위해 모두 널러블 처리
// set 사용하면 안돼 ! private => set사용못하는데 get도 사용못하는데 그래서 밑에 get 선언
// get 을 할 대 널인지아닌지 체크 -> !! null 아님!!

// 이렇게해야 프론트가편함 , dto 여러개 안만들어도됨
data class MemberDtoRequest(
    var id : Long?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^[a-z0-9]{4,10}\$",
        message = "아이디 소문자, 숫자를 포함한 4~10자리로 입력.",
    )
    @JsonProperty("userName") // typedef 임, 변수이름 바꿔주기, json으로 내보낼때 원래 변수명으로 보내야됨 (우리가 _붙여놨었음)
    private val _userName : String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^[a-zA-Z0-9]{8,15}\$",
        message= "비밀번호 영문, 숫자만 포함한 8~15자리로 입력."
    )
    @JsonProperty("password")
    private val _password : String?,

    @field:NotBlank
    @JsonProperty("nickName")
    private val _nickName : String?,
){
    val userName : String
        get() = _userName!!
    val password : String
        get() = _password!!
    val nickName : String
        get() = _nickName!!

    fun toEntity() : Member = Member(id, userName, password, nickName)
}

data class LoginDto(
    @field:NotBlank
    @JsonProperty("userName")
    private val _userName : String?,

    @field:NotBlank
    @JsonProperty("password")
    private val _password : String?,
){
    val userName : String
    get() = _userName!!
    val password : String
    get() = _password!!
}

data class MemberDtoResponse(
    val id : Long?,
    val userName : String?,
    val nickName : String?,
)