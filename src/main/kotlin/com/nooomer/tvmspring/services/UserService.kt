package com.nooomer.tvmspring.services

import com.nooomer.tvmspring.db.models.Role
import com.nooomer.tvmspring.db.models.User
import com.nooomer.tvmspring.db.repositories.UsersRepository
import com.nooomer.tvmspring.dto.LoginDataDto
import com.nooomer.tvmspring.dto.UserModifyDto
import com.nooomer.tvmspring.dto.UsersDto
import com.nooomer.tvmspring.dto.UsersRegistrationDto
import com.nooomer.tvmspring.exceptions.AlreadyAuthorizeException
import com.nooomer.tvmspring.exceptions.NotAuthorizeException
import com.nooomer.tvmspring.exceptions.UserNotFoundException
import com.nooomer.tvmspring.services.helpers.SecurityContextSessionHelper
import jakarta.transaction.Transactional
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    var usersRepository: UsersRepository,
    val authManager: AuthenticationManager,
    val passwordEncoder: PasswordEncoder,
    val securityContextSessionHelper: SecurityContextSessionHelper,
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

    private fun getCurrentUserPhone(): Any? {
        return securityContextSessionHelper.getSessionData().authentication.principal
    }

    @Transactional
    fun getCurrentUser(): UsersDto {
        val user: User = getCurrentUserPhone() as User
        return usersRepository.findByPhoneNumberP(user.phoneNumberP).orElseThrow {
            UserNotFoundException("User with phone=$user not found")
        }.toUserDto()
    }

    private fun checkAlreadyLogin(): Boolean {
        try {
            securityContextSessionHelper.getSessionData()
        } catch (ex: NotAuthorizeException) {
            return false
        }
        return true
    }

    fun login(loginData: LoginDataDto): UsersDto {
        if (checkAlreadyLogin()) {
            throw AlreadyAuthorizeException("You already login")
        }
        authenticateAndSetContext(loginData)
        securityContextSessionHelper.setSessionData(SecurityContextHolder.getContext())
        return getCurrentUser()
    }

    private fun authenticateAndSetContext(loginData: LoginDataDto) {
        val authentication: Authentication =
            authManager.authenticate(UsernamePasswordAuthenticationToken(loginData.phoneNumber, loginData.password))
        SecurityContextHolder.getContext().authentication = authentication
    }

    fun logout() {
        if (!checkAlreadyLogin()) {
            throw NotAuthorizeException("you must be logged in to be able to log out")
        }
        securityContextSessionHelper.deleteSession()
    }

}
