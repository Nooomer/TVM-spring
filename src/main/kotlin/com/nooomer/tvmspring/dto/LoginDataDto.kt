package com.nooomer.tvmspring.dto

import jakarta.validation.constraints.Email

data class LoginDataDto(
    var phoneNumber: String,
    var password: String
)
