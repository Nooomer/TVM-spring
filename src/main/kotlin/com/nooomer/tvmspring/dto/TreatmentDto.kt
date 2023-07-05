package com.nooomer.tvmspring.dto

import java.io.Serializable

/**
 * A DTO for the {@link com.nooomer.tvmspring.db.models.Treatment} entity
 */
data class TreatmentDto(
    val id: Int? = null,
    val startDate: String? = null,
    val symptomsId: Int? = null,
    val soundServerLinkId: String? = null,
    val conclusionId: Int? = null,
    val chatsById: MutableCollection<ChatsDto?>? = null,
    val chats: ChatsDto? = null,
    val users: UsersDto? = null,
    val usersByDoctorId: UsersDto? = null
) : Serializable