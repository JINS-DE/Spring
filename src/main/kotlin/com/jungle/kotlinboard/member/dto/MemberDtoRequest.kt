package com.jungle.kotlinboard.member.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.jungle.kotlinboard.member.domain.Member
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

// 다양한 용도로 사용하기 위해 모든 필드를 nullable로 처리하여 필요한 경우 null 값을 허용
// 필드들은 private로 선언되어 set 함수를 사용할 수 없으므로, 값을 가져올 때 직접적으로 get 함수를 사용하도록 정의
// 각 필드에 대해 get을 할 때 null이 아닌지 확인하여 null 안전성을 보장 (!! 연산자 사용)
// 이러한 설계로 프론트엔드가 여러 가지 DTO를 생성하지 않고도 이 DTO를 재사용하기 편리하도록 합니다.
data class MemberDtoRequest(
    var id : Long?,

    @field:NotBlank // 필드 빈 값 X -> null or "", " " 유효하지 않음
    @field:Pattern( // 필드 값 지정된 형식에 맞는지 확인
        regexp = "^[a-z0-9]{4,10}\$",
        message = "아이디 소문자, 숫자를 포함한 4~10자리로 입력.", // 검증에 실패했을 때 사용자에게 표시될 메시지 설정
    )
    @JsonProperty("userName") // _userName 필드를 JSON으로 내보낼 때 "userName"이라는 이름으로 변환
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
    // !! : 절대 null이 아님
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