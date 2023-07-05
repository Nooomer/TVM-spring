package com.nooomer.tvmspring.services

import com.nooomer.tvmspring.db.models.Role
import com.nooomer.tvmspring.db.models.User
import com.nooomer.tvmspring.db.repositories.UsersRepository
import com.nooomer.tvmspring.dto.LoginDataDto
import com.nooomer.tvmspring.dto.UserModifyDto
import com.nooomer.tvmspring.dto.UsersDto
import com.nooomer.tvmspring.dto.UsersRegistrationDto
import com.nooomer.tvmspring.exceptions.UserNotFoundException
import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    var usersRepository: UsersRepository,
    val authManager: AuthenticationManager,
    val passwordEncoder: PasswordEncoder,
) {

    fun User.toUserDto() = UsersDto(
        id = this.id!!,
        surename = this.surename,
        name = this.name,
        sName = this.sName,
        phoneNumber = this.phoneNumberP,
        insurancePolicyNumber = this.insurancePolicyNumber,
        password = this.password,
        userType = this.userType,
        roles = this.roles
    )

    fun UsersRegistrationDto.toUser() = User(
        surename = surename,
        name = name,
        sName = sName,
        phoneNumberP = phoneNumber,
        insurancePolicyNumber = insurancePolicyNumber,
        passwordP = password,
        userType = userType,
        roles = mutableSetOf(Role(role))
    )

    fun UserModifyDto.checkEqual(user: User): User {
        if ((this.name != user.name) and (this.name != null)) {
            user.name = name.toString()
        }
        if ((this.userType != user.userType) and (this.userType != null)) {
            user.userType = userType.toString()
        }
        if ((this.insurancePolicyNumber != user.insurancePolicyNumber) and (this.insurancePolicyNumber != null)) {
            user.insurancePolicyNumber = insurancePolicyNumber.toString()
        }
        if ((this.password != user.password) and (this.password != null)) {
            user.passwordP = password.toString()
        }
        if ((this.sName != user.sName) and (this.sName != null)) {
            user.sName = sName.toString()
        }
        if ((this.surename != user.surename) and (this.surename != null)) {
            user.surename = surename.toString()
        }
        return user
    }

    fun MutableList<User>.toUserDtoList(): MutableList<UsersDto> {
        val list = mutableListOf<UsersDto>()
        this.forEach {
            list.add(it.toUserDto())
        }
        return list
    }

    fun modifyUserByPhone(userModifyDto: UserModifyDto): UsersDto {
        val user = usersRepository.findByPhoneNumberP(userModifyDto.phoneNumber).orElseThrow {
            UserNotFoundException("User with phone number ${userModifyDto.phoneNumber} not found")
        }
        userModifyDto.id = user.id!!
        val newUser = userModifyDto.checkEqual(user)
        usersRepository.save(newUser)
        return newUser.toUserDto()
    }

    fun getAllUsers(): MutableList<UsersDto> {
        return usersRepository.findAll().toUserDtoList()
    }

    fun addUser(usersRegistrationDto: UsersRegistrationDto): UsersDto {
        var user: User = usersRegistrationDto.toUser()
        user.passwordP = passwordEncoder.encode(usersRegistrationDto.password)
        user = usersRepository.save(user)
        return user.toUserDto()
    }

    fun getUserByPhoneNumber(phoneNumber: String): UsersDto {
        return usersRepository.findByPhoneNumberP(phoneNumber).orElseThrow {
            UserNotFoundException("User with phone number $phoneNumber not found")
        }.toUserDto()
    }

    private fun getCurrentUserPhone(request: HttpServletRequest): Any? {
        return (request.session.getAttribute("SPRING_SECURITY_CONTEXT") as SecurityContext).authentication.principal
    }

    @Transactional
    fun getCurrentUser(request: HttpServletRequest): UsersDto {
        val user: User = getCurrentUserPhone(request) as User
        return usersRepository.findByPhoneNumberP(user.phoneNumberP).orElseThrow {
            UserNotFoundException("User with phone=$user not found")
        }.toUserDto()
    }

    fun login(loginData: LoginDataDto, request: HttpServletRequest): UsersDto {
        val authentication: Authentication =
            authManager.authenticate(UsernamePasswordAuthenticationToken(loginData.phoneNumber, loginData.password))
        SecurityContextHolder.getContext().authentication = authentication
        request.session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext())
        return getCurrentUser(request)
    }

    fun logout(request: HttpServletRequest) {
        SecurityContextHolder.getContext().authentication = null
        request.session.invalidate()
    }

}
