package com.nooomer.tvmspring.db.models

import jakarta.persistence.*

@Entity
@Table(name = "treatment")
open class Treatment: Base() {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chat_id", nullable = false)
    open var chat: Chat? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    open var patient: com.nooomer.tvmspring.db.models.User? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    open var doctor: com.nooomer.tvmspring.db.models.User? = null

    @Column(name = "start_date", nullable = false, length = Integer.MAX_VALUE)
    open var startDate: String? = null

    @Column(name = "symptoms_id", nullable = false)
    open var symptomsId: Int? = null

    @Column(name = "sound_server_link_id", nullable = false, length = Integer.MAX_VALUE)
    open var soundServerLinkId: String? = null

    @Column(name = "conclusion_id", nullable = false)
    open var conclusionId: Int? = null
}