package com.nooomer.tvmspring.dto

import com.nooomer.tvmspring.dto.MessagesDto
import com.nooomer.tvmspring.dto.TreatmentDto
import java.io.Serializable

/**
 * A DTO for the {@link com.nooomer.tvmspring.db.models.Chats} entity
 */
data class ChatsDto(
    val id: Int? = null,
    val treatment: TreatmentDto? = null,
    val messagesById: MutableCollection<MessagesDto?>? = null,
    val treatmentsById: MutableCollection<TreatmentDto?>? = null
) : Serializable