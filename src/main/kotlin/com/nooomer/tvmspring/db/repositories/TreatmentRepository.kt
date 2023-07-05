package com.nooomer.tvmspring.db.repositories;

import com.nooomer.tvmspring.db.models.Treatment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TreatmentRepository : JpaRepository<Treatment, UUID> {
}