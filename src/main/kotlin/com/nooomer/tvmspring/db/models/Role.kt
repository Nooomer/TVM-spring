package com.nooomer.tvmspring.db.models

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "role")
open class Role(
    open var name: String,
) : Base() {
    enum class RoleName(val value: String) {
        ADMIN("ROLE_ADMIN"), PATIENT("ROLE_PATIENT"), DOCTOR("ROLE_DOCTOR");

        open fun getName(): String? {
            return name
        }
    }

}