package com.nooomer.tvmspring.db.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "messages")
open class Message(
    @Column(name = "message_text", nullable = false, length = Integer.MAX_VALUE)
    open var messageText: String,

    @Column(name = "from_id", nullable = false, length = Integer.MAX_VALUE)
    open var from: UUID,

    @Column(name = "to_id", nullable = false, length = Integer.MAX_VALUE)
    open var to: UUID,
) : Base() {
}