package com.nooomer.tvmspring.dto

import java.io.Serializable

data class UsersRegistrationDto(
    val surename: String,
    val name: String,
    val sName: String,
    val phoneNumber: String,
    val insurancePolicyNumber: String,
    val password: String,
    val userType: String,
    val role: String,
    val messagesById: MutableCollection<MessagesDto?>?,
    val treatmentsById: MutableCollection<TreatmentDto?>?,
) : Serializable