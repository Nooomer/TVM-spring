package com.nooomer.tvmspring.dto

import java.io.Serializable
import java.util.*

data class UserModifyDto(
    var id: UUID?,
    var surename: String?,
    var name: String?,
    var sName: String?,
    var phoneNumber: String,
    var insurancePolicyNumber: String?,
    var password: String?,
    var userType: String?,
    var messagesById: MutableCollection<MessageDto?>?,
    var treatmentsById: MutableCollection<TreatmentDto?>?,
) : Serializable