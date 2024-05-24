package com.nooomer.tvmspring.controllers

import com.nooomer.tvmspring.dto.ChatDto
import com.nooomer.tvmspring.dto.NewMessageDto
import com.nooomer.tvmspring.dto.SoundDto
import com.nooomer.tvmspring.services.ChatService
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
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

    @PutMapping
    fun sendAudio(
        @RequestPart("file") file: MultipartFile,
        @PathVariable("treatment-id") id: UUID,
    ): ResponseEntity<Any> {
        val result = chatService.addSound(id, file)
        if (!result) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @GetMapping("/audio/{count}")
    fun getAudioForTreatment(
        @PathVariable("treatment-id") id: UUID,
        @PathVariable("count") count: Int,
    ): ResponseEntity<Resource> {
        val file = chatService.getSound(id, count)
        return if (file != null) {
            ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.filename + "\""
            ).body(file)
        } else{
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @GetMapping("/audio")
    fun getAllAudioForTreatment(
        @PathVariable("treatment-id") id: UUID,
    ): ResponseEntity<MutableSet<SoundDto>> {
        return ResponseEntity.ok(chatService.getAllSound(id))
    }
}