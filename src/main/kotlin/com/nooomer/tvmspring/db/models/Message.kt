package com.nooomer.tvmspring.db.models

import jakarta.persistence.*

@Entity
@Table(name = "messages")
open class Message: Base() {
    @Column(name = "message_text", nullable = false, length = Integer.MAX_VALUE)
    open var messageText: String? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chat_id", nullable = false)
    open var chat: Chat? = null

    @Column(name = "send_time", nullable = false, length = Integer.MAX_VALUE)
    open var sendTime: String? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_id", nullable = false)
    open var from: com.nooomer.tvmspring.db.models.User? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_id", nullable = false)
    open var to: com.nooomer.tvmspring.db.models.User? = null
}