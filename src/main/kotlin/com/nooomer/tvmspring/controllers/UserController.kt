package com.nooomer.tvmspring.controllers

import com.nooomer.tvmspring.dto.UserModifyDto
import com.nooomer.tvmspring.dto.UsersDto
import com.nooomer.tvmspring.dto.UsersRegistrationDto
import com.nooomer.tvmspring.services.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController(var userService: UserService) {
    @GetMapping()
    fun getUsers(): ResponseEntity<MutableList<UsersDto>> {
        return ResponseEntity.ok(userService.getAllUsers())
    }

    @GetMapping("/logout")
    fun logout(request: HttpServletRequest): ResponseEntity.BodyBuilder {
        userService.logout(request)
        return ResponseEntity.ok()
    }

    @GetMapping("/{phoneNumber}")
    fun getUsersByPhoneNumber(@PathVariable phoneNumber: String): ResponseEntity<UsersDto> {
        return ResponseEntity.ok(userService.getUserByPhoneNumber(phoneNumber))
    }
    @GetMapping("/me")
    fun getCurrentUser(request: HttpServletRequest): ResponseEntity<UsersDto> {
        return ResponseEntity.ok(userService.getCurrentUser(request))
    }

    @PutMapping()
    fun addUser(@RequestBody usersRegistrationDto: UsersRegistrationDto): ResponseEntity<UsersDto> {
        return ResponseEntity.ok(userService.addUser(usersRegistrationDto))
    }

    @PatchMapping()
    fun modifyUser(@RequestBody userModifyDto: UserModifyDto): ResponseEntity<UsersDto> {
        return ResponseEntity.ok(userService.modifyUserByPhone(userModifyDto))
    }

}