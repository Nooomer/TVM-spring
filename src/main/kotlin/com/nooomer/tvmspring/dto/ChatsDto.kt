package com.nooomer.tvmspring.dto

import java.io.Serializable


data class ChatsDto(
    val id: Int? = null,
    val treatment: TreatmentDto? = null,
    val messagesById: MutableCollection<MessagesDto?>? = null,
    val treatmentsById: MutableCollection<TreatmentDto?>? = null,
) : Serializable