package com.nooomer.tvmspring.services

import com.nooomer.tvmspring.db.models.User
import com.nooomer.tvmspring.db.repositories.UsersRepository
import com.nooomer.tvmspring.dto.*
import com.nooomer.tvmspring.exceptions.AlreadyAuthorizeException
import com.nooomer.tvmspring.exceptions.NotAuthorizeException
import com.nooomer.tvmspring.exceptions.UserNotFoundException
import com.nooomer.tvmspring.services.helpers.Converter.toUser
import com.nooomer.tvmspring.services.helpers.Converter.toUserDto
import com.nooomer.tvmspring.services.helpers.Converter.toUserDtoList
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
    val roleService: RoleService,
) {

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

    fun modifyUserByPhone(userModifyDto: UserModifyDto): UsersDto {
        val user = usersRepository.findByPhoneNumberP(userModifyDto.phoneNumber).orElseThrow {
            UserNotFoundException("User with phone number ${userModifyDto.phoneNumber} not found")
        }
        userModifyDto.id = user.id!!
        val newUser = userModifyDto.checkEqual(user)
        usersRepository.save(newUser)
        return newUser.toUserDto()
    }

    fun getAllUsers(): List<UsersDto> {
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
    fun getCurrentUserDto(): UsersDto {
        val user: User = getCurrentUserPhone() as User
        return usersRepository.findByPhoneNumberP(user.phoneNumberP).orElseThrow {
            UserNotFoundException("User with phone=$user not found")
        }.toUserDto()
    }

    @Transactional
    fun getCurrentUser(): User {
        val user: User = getCurrentUserPhone() as User
        return usersRepository.findByPhoneNumberP(user.phoneNumberP).orElseThrow {
            UserNotFoundException("User with phone=$user not found")
        }
    }

    fun checkAlreadyLogin(): Boolean {
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
        return getCurrentUserDto()
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

    fun setRole(setNewRoleDto: SetNewRoleDto): UsersDto {
        val role = roleService.getRoleByName(setNewRoleDto.roleName)
        val user = usersRepository.findById(setNewRoleDto.userId).orElseThrow{
            UserNotFoundException("User with id=${setNewRoleDto.userId} not found")
        }
            user.roles.add(role)
        val newUser = usersRepository.save(user)
        return newUser.toUserDto()
    }

    fun deleteRoleForUser(setNewRoleDto: SetNewRoleDto): UsersDto {
        val role = roleService.getRoleByName(setNewRoleDto.roleName)
        val user = usersRepository.findById(setNewRoleDto.userId).orElseThrow{
            UserNotFoundException("User with id=${setNewRoleDto.userId} not found")
        }
        user.roles.remove(role)
        val newUser = usersRepository.save(user)
        return newUser.toUserDto()
    }
}

