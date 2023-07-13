package com.nooomer.tvmspring.dto

import java.io.Serializable
import java.util.*

data class NewTreatmentDto(
    var patient: UsersDto?,
    var symptomIds: MutableSet<UUID>,
) : Serializable