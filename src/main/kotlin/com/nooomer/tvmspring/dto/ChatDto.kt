package com.nooomer.tvmspring.dto

import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

data class ChatDto(
    var id: UUID,
    var createdDate: LocalDateTime,
    var updatedDate: LocalDateTime?,
    var message: MutableSet<MessageDto>?,
) : Serializable