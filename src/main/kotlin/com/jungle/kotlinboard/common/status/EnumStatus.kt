package com.jungle.kotlinboard.common.status

enum class ResultCode(val msg:String) {
    SUCCESS("정상 처리되었습니다."),
    ERROR("에러가 발생했습니다.")
}

enum class ROLE{
    MEMBER
}