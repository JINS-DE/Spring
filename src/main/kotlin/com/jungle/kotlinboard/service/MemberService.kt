package com.jungle.kotlinboard.service
import com.jungle.kotlinboard.domain.Member
import com.jungle.kotlinboard.domain.MemberRepository
import com.jungle.kotlinboard.dto.MemberDtoRequest
import com.jungle.kotlinboard.exception.InvalidInputException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
@Transactional
class MemberService (
    private val memberRepository : MemberRepository
){
    fun signUp(memberDtoRequest : MemberDtoRequest):String{
        var member: Member? = memberRepository.findByUserName(memberDtoRequest.userName)
        if (member !=null){
            throw InvalidInputException("이미 등록된 ID 입니다.")
        }

        member = Member(
            null,
            memberDtoRequest.userName,
            memberDtoRequest.password,
            memberDtoRequest.nickName,
        )
        memberRepository.save(member)
        return "회원가입이 완료되었습니다."
    }

    fun login(user:UserLoginRequest) : Map<String,Any> {
        val member = memberRepository.findById(user.userName).orElse(null)

        if (member == null) {
            return mapOf("message" to "없는 아이디입니다.")
        } else if(member.password != user.password) {
            return mapOf("message" to "비밀번호가 틀립니다.")
        } else {
            return mapOf(
                "message" to "로그인 성공",
                "userName" to user.userName
            )
        }
    }

    fun userList():List<Member> = memberRepository.findAll()


//    fun login(userName:String, pw:String){
//        val user = userRepository.findById(userName).orElseThrow()
//
//    }
}