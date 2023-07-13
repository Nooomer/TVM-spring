package com.nooomer.tvmspring.dto

import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

data class MessageDto(
    var id: UUID,
    var createdDate: LocalDateTime,
    var updatedDate: LocalDateTime?,
    var messageText: String,
    var from: UUID,
    var to: UUID,
) : Serializable