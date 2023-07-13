package com.nooomer.tvmspring.controllers

import com.nooomer.tvmspring.dto.LoginDataDto
import com.nooomer.tvmspring.dto.UsersDto
import com.nooomer.tvmspring.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user/login")
class LoginController(var userService: UserService) {
    @GetMapping
    fun alreadyLogin(): ResponseEntity<Boolean> {
        return ResponseEntity.ok(userService.checkAlreadyLogin())
    }

    @PostMapping
    fun login(@RequestBody loginDataDto: LoginDataDto): ResponseEntity<UsersDto> {
        return ResponseEntity.ok(userService.login(loginDataDto))
    }

}