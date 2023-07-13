package com.nooomer.tvmspring.dto

import java.io.Serializable

data class UsersRegistrationDto(
    var surename: String,
    var name: String,
    var sName: String,
    var phoneNumber: String,
    var insurancePolicyNumber: String,
    var password: String,
    var userType: String,
    var role: String,
    var messagesById: MutableCollection<MessageDto?>?,
    var treatmentsById: MutableCollection<TreatmentDto?>?,
) : Serializable