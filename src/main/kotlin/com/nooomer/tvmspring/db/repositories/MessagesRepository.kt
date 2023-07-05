package com.nooomer.tvmspring.db.repositories;

import com.nooomer.tvmspring.db.models.Message
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MessagesRepository : JpaRepository<Message, UUID> {
}