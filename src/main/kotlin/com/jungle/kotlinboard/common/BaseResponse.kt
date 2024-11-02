package com.jungle.kotlinboard.common

import com.jungle.kotlinboard.common.status.ResultCode

data class BaseResponse<T> (
    val resultCode:String = ResultCode.SUCCESS.name,
    val data: T? = null,
    val message:String = ResultCode.SUCCESS.msg,
)