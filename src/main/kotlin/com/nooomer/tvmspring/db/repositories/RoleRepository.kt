package com.nooomer.tvmspring.db.repositories;

import com.nooomer.tvmspring.db.models.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<Role, UUID> {
    fun findByName(name: String): Optional<Role>
}