package com.nooomer.tvmspring.db.models

import jakarta.persistence.*

@Entity
@Table(name = "chats")
open class Chat: Base() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treatment_id")
    open var treatment: Treatment? = null

    @OneToMany(mappedBy = "chat")
    open var messages: MutableSet<Message> = mutableSetOf()

    @OneToMany(mappedBy = "chat")
    open var treatments: MutableSet<Treatment> = mutableSetOf()
}