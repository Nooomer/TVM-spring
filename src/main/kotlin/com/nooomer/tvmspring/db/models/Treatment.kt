package com.nooomer.tvmspring.db.models

import jakarta.persistence.*

@Entity
@Table(name = "treatment")
open class Treatment(
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "chat_id", nullable = true)
    open var chat: Chat?,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    open var patient: User,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "doctor_id", nullable = true)
    open var doctor: User?,

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "conclusion_id", nullable = true)
    open var conclusion: Conclusion?,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "sound_link_id", nullable = true)
    open var sound: MutableSet<Sound>?,

    @ManyToMany
    @JoinTable(
        name = "treatment_symptom",
        joinColumns = [JoinColumn(name = "treatment_id")],
        inverseJoinColumns = [JoinColumn(name = "symptom_id")]
    )
    open var symptoms: MutableSet<Symptom> = mutableSetOf(),
) : Base()