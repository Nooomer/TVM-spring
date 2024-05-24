package com.nooomer.tvmspring.controllers

import com.nooomer.tvmspring.dto.ChatDto
import com.nooomer.tvmspring.dto.NewMessageDto
import com.nooomer.tvmspring.services.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("treatment/chat/{treatment-id}")
class ChatController(
    private val chatService: ChatService,
) {
    @GetMapping()
    fun getChatForTreatment(@PathVariable("treatment-id") id: UUID): ResponseEntity<ChatDto> {
        return ResponseEntity.ok(chatService.getChatForTreatment(id))
    }

    @PostMapping()
    fun sendMessage(
        @PathVariable("treatment-id") id: UUID,
        @RequestBody message: NewMessageDto,
    ): ResponseEntity<ChatDto> {
        return ResponseEntity.ok(chatService.sendMessage(message, id))
    }
}