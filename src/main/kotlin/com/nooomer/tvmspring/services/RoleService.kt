package com.nooomer.tvmspring.services

import com.nooomer.tvmspring.db.models.Role
import com.nooomer.tvmspring.db.repositories.RoleRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.management.relation.RoleNotFoundException

@Service
class RoleService(
    private val roleRepository: RoleRepository
) {

    fun getRoleById(roleId: UUID): Role {
        return roleRepository.findById(roleId).orElseThrow {
            RoleNotFoundException("Role with id=${roleId} not found")
        }
    }

    fun getRoleByName(roleName: String): Role {
        return roleRepository.findByName(roleName).orElseThrow {
            RoleNotFoundException("Role with name=${roleName} not found")
        }
    }

}