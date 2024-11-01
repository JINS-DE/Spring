package com.jungle.kotlinboard.common

data class BaseResponse<T> (
    val resultCode:String = ResultCode.SUCCESS.name,
    val data: T? = null,
    val message:String = ResultCode.SUCCESS.msg,
)