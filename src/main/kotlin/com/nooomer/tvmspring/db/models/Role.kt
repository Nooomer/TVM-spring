package com.nooomer.tvmspring.db.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "role")
open class Role(
    open var name:String
    ) : Base() {
    enum class RoleName(val value: String) {
        ADMIN("ROLE_ADMIN"), EMPLOYER("ROLE_EMPLOYER"), VIEWER("ROLE_VIEWER");
        open fun getName(): String? {
            return name
        }
    }

}