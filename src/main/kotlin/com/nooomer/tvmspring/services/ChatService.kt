package com.nooomer.tvmspring.services

import com.nooomer.tvmspring.dto.ChatDto
import com.nooomer.tvmspring.dto.TreatmentDto
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ChatService(
    private val treatmentService: TreatmentService,
) {
    fun getChatForTreatment(treatmentId: UUID): ChatDto? {
        return treatmentService.getTreatmentById(treatmentId).chat
    }
}