package com.nooomer.tvmspring.db.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "conclusion")
open class Conclusion(
    @Column(name = "conclusion_text", nullable = false, length = Integer.MAX_VALUE)
    open var conclusionText: String,
) : Base()