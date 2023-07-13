package com.nooomer.tvmspring.db.repositories;

import com.nooomer.tvmspring.db.models.Conclusion
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ConclusionRepository : JpaRepository<Conclusion, UUID> {
}