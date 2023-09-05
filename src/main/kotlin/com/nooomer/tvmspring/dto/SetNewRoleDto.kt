package com.nooomer.tvmspring.dto

import java.util.*

data class SetNewRoleDto(
    var roleName: String,
    var userId: UUID
)