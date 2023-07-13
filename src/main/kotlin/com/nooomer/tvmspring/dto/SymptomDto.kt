package com.nooomer.tvmspring.dto

import java.time.LocalDateTime
import java.util.*

data class SymptomDto(
    var id: UUID,
    var createdDate: LocalDateTime,
    var updatedDate: LocalDateTime?,
    var symptomsName: String,
)