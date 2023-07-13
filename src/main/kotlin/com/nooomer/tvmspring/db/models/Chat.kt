package com.nooomer.tvmspring.db.models

import jakarta.persistence.*

@Entity
@Table(name = "chats")
open class Chat(
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "messages_id", nullable = true)
    open var message: MutableSet<Message>?,
) : Base() {
}