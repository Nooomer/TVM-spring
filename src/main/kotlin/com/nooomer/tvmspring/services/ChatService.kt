package com.nooomer.tvmspring.services

import com.nooomer.tvmspring.db.models.Chat
import com.nooomer.tvmspring.db.models.Message
import com.nooomer.tvmspring.db.repositories.ChatsRepository
import com.nooomer.tvmspring.db.repositories.MessagesRepository
import com.nooomer.tvmspring.dto.ChatDto
import com.nooomer.tvmspring.dto.NewMessageDto
import com.nooomer.tvmspring.services.helpers.Converter.toChat
import com.nooomer.tvmspring.services.helpers.Converter.toMessageDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatService(
    private val treatmentService: TreatmentService,
    private val userService: UserService,
    private val messagesRepository: MessagesRepository,
    private val chatsRepository: ChatsRepository
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private var treatmentId: UUID? = null
    fun getChatForTreatment(treatmentId: UUID?): ChatDto? {
        return treatmentService.getTreatmentById(checkNotNull(treatmentId)).chat
    }
    fun setTreatmentId(treatmentId: UUID) {
        logger.debug("TreatmentId = {}", treatmentId)
        this.treatmentId = treatmentId
    }

    private fun getMessageCount(): Int {
        val treatment = getChatForTreatment(treatmentId)
        return treatment?.messages!!.count()
    }

    fun sendMessage(message: NewMessageDto, treatmentId: UUID): ChatDto? {
        val chatDto = getChatForTreatment(treatmentId)
        val currentUserId = userService.getCurrentUser().id
        val treatment = treatmentService.getTreatmentById(treatmentId)
        var to: UUID? = null
        when{
            treatment.doctor?.id == currentUserId -> to=treatment.patient.id
            treatment.patient.id == currentUserId -> to=treatment.doctor?.id
        }
        val newMessage = Message(
            message.messageText,
            currentUserId!!,
            to!!
        )
        val createdMessage = messagesRepository.save(newMessage)
        chatDto?.messages?.add(createdMessage.toMessageDto())
        val newChat = chatDto.toChat()
        chatsRepository.save(newChat)
        return getChatForTreatment(treatmentId)
    }

}