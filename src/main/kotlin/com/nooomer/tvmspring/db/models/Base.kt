package com.nooomer.tvmspring.db.models

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

@MappedSuperclass
open class Base : Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "id")
    open var id: UUID? = null

    @Column(name = "created_date")
    open var createdDate: LocalDateTime? = null

    @Column(name = "updated_date")
    open var updatedDate: LocalDateTime? = null
    @PrePersist
    fun onCreate() {
        createdDate = LocalDateTime.now()
    }

    @PreUpdate
    fun onUpdate() {
        updatedDate = LocalDateTime.now()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val base = o as Base
        return if (id != null) id == base.id else base.id == null
    }

    override fun hashCode(): Int {
        return if (id != null) id.hashCode() else 0
    }
}