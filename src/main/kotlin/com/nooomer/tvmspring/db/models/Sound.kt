package com.nooomer.tvmspring.db.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "sound")
open class Sound(
    @Column(name = "sound_server_link", nullable = false, length = Integer.MAX_VALUE)
    open var soundServerLink: String,
) : Base()