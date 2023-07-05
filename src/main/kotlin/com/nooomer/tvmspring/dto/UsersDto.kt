package com.nooomer.tvmspring.dto

import com.nooomer.tvmspring.db.models.Role
import java.io.Serializable
import java.util.*

data class UsersDto(
    var id: UUID,
    var surename: String,
    var name: String,
    var sName: String,
    var phoneNumber: String,
    var insurancePolicyNumber: String,
    var password: String,
    var userType: String,
    var roles: MutableSet<Role>,
    var messagesById: MutableCollection<MessagesDto?>? = null,
    var treatmentsById: MutableCollection<TreatmentDto?>? = null,
) : Serializable