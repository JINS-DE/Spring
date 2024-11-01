package com.jungle.kotlinboard.exception

// 개발자가 잡을 수 있는 Exception = runtime 익셉션
class InvalidInputException (
    val fieldName: String = "",
    message:String = "Invalid Input"
) : RuntimeException(message)