package com.nooomer.tvmspring.db.repositories;

import com.nooomer.tvmspring.db.models.Symptom
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SymptomRepository : JpaRepository<Symptom, UUID> {
}