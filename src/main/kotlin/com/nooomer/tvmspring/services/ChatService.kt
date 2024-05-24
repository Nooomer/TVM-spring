package com.nooomer.tvmspring.services

import com.nooomer.tvmspring.db.models.Message
import com.nooomer.tvmspring.db.models.Sound
import com.nooomer.tvmspring.db.repositories.ChatsRepository
import com.nooomer.tvmspring.db.repositories.MessagesRepository
import com.nooomer.tvmspring.db.repositories.SoundRepository
import com.nooomer.tvmspring.dto.ChatDto
import com.nooomer.tvmspring.dto.NewMessageDto
import com.nooomer.tvmspring.dto.SoundDto
import com.nooomer.tvmspring.services.helpers.Converter.toChat
import com.nooomer.tvmspring.services.helpers.Converter.toMessageDto
import com.nooomer.tvmspring.services.helpers.Converter.toSoundDto
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class ChatService(
    private val treatmentService: TreatmentService,
    private val userService: UserService,
    private val messagesRepository: MessagesRepository,
    private val chatsRepository: ChatsRepository,
    private val storageService: StorageService,
    private val soundRepository: SoundRepository,
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private var treatmentId: UUID? = null
    fun getChatForTreatment(treatmentId: UUID?): ChatDto? {
        return treatmentService.getTreatmentDtoById(checkNotNull(treatmentId)).chat
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
        val treatment = treatmentService.getTreatmentDtoById(treatmentId)
        var to: UUID? = null
        when {
            treatment.doctor?.id == currentUserId -> to = treatment.patient.id
            treatment.patient.id == currentUserId -> to = treatment.doctor?.id
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

    fun addSound(treatmentId: UUID, file: MultipartFile): Boolean {
        val currentUser = userService.getCurrentUser()
        val treatment = treatmentService.getTreatmentById(treatmentId)
        val result = storageService.store(file)
        if (result) {
            val newSound = soundRepository.save(
                Sound(
                    "${file.originalFilename}",
                )
            )
            treatment.sound!!.add(newSound)
            treatmentService.updateTreatmentAfterUploadSound(treatment)
            return true
        } else {
            return false
        }
    }

    fun getSound(treatmentId: UUID, count: Int): Resource? {
        val treatment = treatmentService.getTreatmentById(treatmentId)
        var neededSound: Sound? = null
        treatment.sound?.forEach {
            if (it.soundServerLink.split("_")[1].split(".")[0] == count.toString()) {
                neededSound = it
            }
        }
        return neededSound?.let { storageService.loadAsResource(it.soundServerLink) }
    }

    fun getAllSound(treatmentId: UUID): MutableSet<SoundDto>? {
        val treatment = treatmentService.getTreatmentById(treatmentId)
        val neededSound: MutableSet<SoundDto> = mutableSetOf()
        return treatment.sound?.mapTo(neededSound) {
            it.toSoundDto()
        }

    }

}