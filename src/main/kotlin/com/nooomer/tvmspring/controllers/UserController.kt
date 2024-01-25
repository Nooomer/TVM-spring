package com.nooomer.tvmspring.controllers

import com.nooomer.tvmspring.dto.SetNewRoleDto
import com.nooomer.tvmspring.dto.UserModifyDto
import com.nooomer.tvmspring.dto.UsersDto
import com.nooomer.tvmspring.dto.UsersRegistrationDto
import com.nooomer.tvmspring.services.UserService
import com.nooomer.tvmspring.services.helpers.interfaces.access.annotation.IsAdmin
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Контроллер для запросов в api/v1/user
 * @constructor автовайред [UserService]
 * @property userService автоподставленное [UserService]
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = ["http://localhost:3000"])
class UserController(var userService: UserService) {
    /**
     * Получаем все пользователей. Доступно только для роли ADMIN
     * @return [List] типа [UsersDto]
     */
    @GetMapping()
    fun getUsers(): ResponseEntity<List<UsersDto>> {
        return ResponseEntity.ok(userService.getAllUsers())
    }

    /**
     * Выход из акаунта и обнуление сессии
     * @param [request] для очистки сессий
     * @return [HttpStatus] - 200
     */
    @GetMapping("/logout")
    fun logout(request: HttpServletRequest): ResponseEntity<Any> {
        userService.logout()
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    /**
     * Получаем юзера по номеру телефона
     * @param phoneNumber номер телефона юзера типа [String]
     * @return [UsersDto]
     */
    @GetMapping("/{phoneNumber}")
    fun getUsersByPhoneNumber(@PathVariable phoneNumber: String): ResponseEntity<UsersDto> {
        return ResponseEntity.ok(userService.getUserByPhoneNumber(phoneNumber))
    }

    @GetMapping("/me")
    fun getCurrentUser(request: HttpServletRequest): ResponseEntity<UsersDto> {
        return ResponseEntity.ok(userService.getCurrentUserDto())
    }

    @PutMapping("/admin")
    @IsAdmin
    fun addUser(@RequestBody usersRegistrationDto: UsersRegistrationDto): ResponseEntity<UsersDto> {
        return ResponseEntity.ok(userService.addUser(usersRegistrationDto))
    }

    @PatchMapping()
    @IsAdmin
    fun modifyUser(@RequestBody userModifyDto: UserModifyDto): ResponseEntity<UsersDto> {
        return ResponseEntity.ok(userService.modifyUserByPhone(userModifyDto))
    }

    @PatchMapping("/role")
    @IsAdmin
    fun setRoleForUser(@RequestBody setNewRoleDto: SetNewRoleDto): ResponseEntity<UsersDto> {
        return ResponseEntity.ok(userService.setRole(setNewRoleDto))
    }

    @DeleteMapping("/role")
    @IsAdmin
    fun deleteRoleForUser(@RequestBody setNewRoleDto: SetNewRoleDto): ResponseEntity<UsersDto> {
        return ResponseEntity.ok(userService.deleteRoleForUser(setNewRoleDto))
    }

}