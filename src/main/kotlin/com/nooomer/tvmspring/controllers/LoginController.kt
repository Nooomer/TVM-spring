package com.nooomer.tvmspring.controllers

import com.nooomer.tvmspring.dto.LoginDataDto
import com.nooomer.tvmspring.dto.UsersDto
import com.nooomer.tvmspring.services.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/user/login")
class LoginController(var userService: UserService) {

    @GetMapping
    fun alreadyLogin(): ResponseEntity<UsersDto> {
       return ResponseEntity.ok(userService.getCurrentUser())
    }

    @PostMapping
    fun login(@RequestBody loginDataDto: LoginDataDto): ResponseEntity<UsersDto> {
       return ResponseEntity.ok(userService.login(loginDataDto))
    }

}