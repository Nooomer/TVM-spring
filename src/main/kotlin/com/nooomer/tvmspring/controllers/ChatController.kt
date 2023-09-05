package com.nooomer.tvmspring.controllers

import com.nooomer.tvmspring.dto.ChatDto
import com.nooomer.tvmspring.services.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("treatment/chat")
class ChatController(
    private val chatService: ChatService,
) {
    @GetMapping()
    fun getChatForTreatment(@RequestBody id: UUID): ResponseEntity<ChatDto> {
        return ResponseEntity.ok(chatService.getChatForTreatment(id))
    }
}