package com.nooomer.tvmspring.dto

import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

data class TreatmentDto(
    var id: UUID,
    var createdDate: LocalDateTime,
    var updatedDate: LocalDateTime?,
    var chat: ChatDto?,
    var patient: UsersDto,
    var doctor: UsersDto?,
    var conclusion: ConclusionDto?,
    var sound: MutableSet<SoundDto>?,
    var symptom: MutableSet<SymptomDto>,
) : Serializable