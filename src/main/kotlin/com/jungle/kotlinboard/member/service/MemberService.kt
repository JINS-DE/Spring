package com.jungle.kotlinboard.member.service
import com.jungle.kotlinboard.common.authority.JwtTokenProvider
import com.jungle.kotlinboard.common.authority.TokenInfo
import com.jungle.kotlinboard.common.status.ROLE
import com.jungle.kotlinboard.member.domain.Member
import com.jungle.kotlinboard.member.domain.MemberRepository
import com.jungle.kotlinboard.member.domain.MemberRole
import com.jungle.kotlinboard.member.domain.MemberRoleRepository
import com.jungle.kotlinboard.member.dto.LoginDto
import com.jungle.kotlinboard.member.dto.MemberDtoRequest
import com.jungle.kotlinboard.member.dto.MemberDtoResponse
import com.jungle.kotlinboard.common.exception.InvalidInputException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service


@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider
){
    fun signUp(memberDtoRequest : MemberDtoRequest):String{
        var member: Member? = memberRepository.findByUserName(memberDtoRequest.userName)
        if (member !=null){
            throw InvalidInputException("이미 등록된 ID 입니다.")
        }
        member = memberDtoRequest.toEntity()
        memberRepository.save(member)

        val memberRole : MemberRole = MemberRole(null, ROLE.MEMBER,member)
        memberRoleRepository.save(memberRole)
//        member = Member(
//            null,
//            memberDtoRequest.userName,
//            memberDtoRequest.password,
//            memberDtoRequest.nickName,
//        )
//        memberRepository.save(member)
        return "회원가입이 완료되었습니다."
    }

    /**
     * 로그인 ->토큰발행
     */
    fun login(loginDto: LoginDto):TokenInfo{
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.userName, loginDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)
    }

    /**
     * 내 정보 조회
     */
    fun searchMyInfo(id: Long): MemberDtoResponse {
        val member = memberRepository.findByIdOrNull(id) ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않는 유저입니다.")
        return member.toDto()
    }

//    fun login(user:UserLoginRequest) : Map<String,Any> {
//        val member = memberRepository.findById(user.userName).orElse(null)
//
//        if (member == null) {
//            return mapOf("message" to "없는 아이디입니다.")
//        } else if(member.password != user.password) {
//            return mapOf("message" to "비밀번호가 틀립니다.")
//        } else {
//            return mapOf(
//                "message" to "로그인 성공",
//                "userName" to user.userName
//            )
//        }
//    }

    // 리스폰스 객체를 만들어라  .
    fun userList():List<Member> = memberRepository.findAll()

    fun saveMyInfo(memberDtoRequest: MemberDtoRequest) :String{
        val member : Member = memberDtoRequest.toEntity()
        memberRepository.save(member)
        return "수정 완료되었습니다."
    }

    /**
     * 회원과 연관된 Role 삭제 후 회원 삭제
     */
    @Transactional
    fun deleteUserById(userId: Long) {
        val user = memberRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with ID $userId not found.") }

        // 해당 Member와 연결된 모든 MemberRole 삭제
        memberRoleRepository.deleteByMember(user)

        // Member 삭제
        memberRepository.delete(user)
    }
//    fun login(userName:String, pw:String){
//        val user = userRepository.findById(userName).orElseThrow()
//
//    }
}