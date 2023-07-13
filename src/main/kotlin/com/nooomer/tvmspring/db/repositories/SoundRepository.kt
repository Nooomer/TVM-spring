package com.nooomer.tvmspring.db.repositories;

import com.nooomer.tvmspring.db.models.Sound
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SoundRepository : JpaRepository<Sound, UUID> {
}