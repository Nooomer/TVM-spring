package com.nooomer.tvmspring.db.repositories;

import com.nooomer.tvmspring.db.models.Chat
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ChatsRepository : JpaRepository<Chat, UUID> {
}