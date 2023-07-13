package com.nooomer.tvmspring.db.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "symptom")
open class Symptom(
    @Column(name = "symptoms_name", nullable = false, length = Integer.MAX_VALUE)
    open var symptomName: String,
    @ManyToMany(mappedBy = "symptoms")
    open var treatments: MutableSet<Treatment> = mutableSetOf(),
) : Base()