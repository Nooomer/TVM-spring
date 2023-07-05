package com.nooomer.tvmspring.db.repositories;

import com.nooomer.tvmspring.db.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UsersRepository : JpaRepository<User, UUID> {
    fun findByPhoneNumberP(phoneNumber: String):Optional<User>
}