package com.nooomer.tvmspring.services.helpers

import com.nooomer.tvmspring.db.models.*
import com.nooomer.tvmspring.dto.*
import java.util.*

object Converter {
    fun User.toUserDto() = UsersDto(
        id = this.id!!,
        surename = this.surename,
        name = this.name,
        sName = this.sName,
        phoneNumber = this.phoneNumberP,
        insurancePolicyNumber = this.insurancePolicyNumber,
        password = this.password,
        userType = this.userType,
        roles = this.roles.toRoleDto()
    )

    fun Role.toRoleDto() = RoleDto(
        id = id!!,
        createdDate = createdDate!!,
        updatedDate = updatedDate,
        name = name
    )

    fun MutableSet<Role>.toRoleDto(): MutableSet<RoleDto> {
        val newSet: MutableSet<RoleDto> = mutableSetOf()
        /*this.forEach {
            newSet.add(it.toRoleDto())
        }*/
        return this.mapTo(newSet){
            it.toRoleDto()
        }
    }

    fun UsersRegistrationDto.toUser() = User(
        surename = surename,
        name = name,
        sName = sName,
        phoneNumberP = phoneNumber,
        insurancePolicyNumber = insurancePolicyNumber,
        passwordP = password,
        userType = userType,
        roles = mutableSetOf(Role(role))
    )

    fun Message.toMessageDto() = MessageDto(
        id = id!!,
        createdDate = createdDate!!,
        updatedDate = updatedDate,
        messageText = messageText,
        from = from,
        to = to
    )

    fun Symptom.toSymptomDto() = SymptomDto(
        id = id!!,
        createdDate = createdDate!!,
        updatedDate = updatedDate,
        symptomsName = symptomName
    )

    fun MutableList<Symptom>.toSymptomDto(): List<SymptomDto> {
        return this.map {
            it.toSymptomDto()
        }
    }

    fun MutableSet<Symptom>?.toSymptomDto(): MutableSet<SymptomDto> {
        val newSet: MutableSet<SymptomDto> = mutableSetOf()
        return this?.mapTo(newSet) {
            it.toSymptomDto()
        }!!
        /*this?.forEach {
            newSet.add(it.toSymptomDto())
        }
        return newSet*/
    }

    fun MutableSet<SymptomDto>?.toSymptom(): MutableSet<Symptom> {
        val newSet: MutableSet<Symptom> = mutableSetOf()
        return this!!.mapTo(newSet){
            it.toSymptom()
        }
    }

    fun SymptomDto.toSymptom() = Symptom(
        symptomName = symptomsName
    )

    fun NewSymptomDto.toSymptom() = Symptom(
        symptomName = symptomName
    )

    fun MutableSet<Sound>?.toSoundDto(): MutableSet<SoundDto> {
        val newSet: MutableSet<SoundDto> = mutableSetOf()
        return this!!.mapTo(newSet){
            it.toSoundDto()
        }
    }

    fun Sound.toSoundDto() = SoundDto(
        id = id!!,
        createdDate = createdDate!!,
        updatedDate = updatedDate,
        soundLink = soundServerLink
    )

    fun MutableSet<Message>?.toMessageDto(): MutableSet<MessageDto> {
        val newSet: MutableSet<MessageDto> = mutableSetOf()
        this?.forEach {
            newSet.add(it.toMessageDto())
        }
        return newSet
    }

    fun MutableSet<MessageDto>?.toMessage(): MutableSet<Message> {
        val newSet: MutableSet<Message> = mutableSetOf()
        this?.map {
            newSet.add(it.toMessage())
        }
        return newSet
    }

    fun MessageDto.toMessage(): Message {
        var newMessage = Message(
            this.messageText,
            this.from,
            this.to
        )
        newMessage.id = this.id
        newMessage.createdDate = this.createdDate
        newMessage.updatedDate = this.updatedDate
        return newMessage
    }

    fun Chat?.toChatDto(): ChatDto? {
        return if (Objects.isNull(this)) {
            null
        } else {
            ChatDto(
                id = this?.id!!,
                createdDate = createdDate!!,
                updatedDate = updatedDate,
                messages = message.toMessageDto()
            )
        }
    }

    fun ChatDto?.toChat(): Chat {
        var newChat = Chat(
            this?.messages.toMessage()
        )
        newChat.id = this?.id
        newChat.createdDate = this?.createdDate
        newChat.updatedDate = this?.updatedDate
        return newChat
    }

    fun Conclusion.toConslusionDto() = ConclusionDto(
        id = id!!,
        createdDate = createdDate!!,
        updatedDate = updatedDate,
        conclusionText = conclusionText
    )

    fun Treatment.toTreatmentDto() = TreatmentDto(
        id = id!!,
        createdDate = createdDate!!,
        updatedDate = updatedDate,
        chat = chat.toChatDto(),
        patient = patient.toUserDto(),
        doctor = doctor?.toUserDto(),
        conclusion = conclusion?.toConslusionDto(),
        sound = sound.toSoundDto(),
        symptom = symptoms.toSymptomDto()
    )

    fun UsersDto.toUser() = User(
        surename = surename,
        name = name,
        sName = sName,
        phoneNumberP = phoneNumber,
        insurancePolicyNumber = insurancePolicyNumber,
        passwordP = password,
        userType = userType,
        roles = roles.toRole()
    )

    fun RoleDto.toRole() = Role(
        name = name
    )

    fun MutableSet<RoleDto>.toRole(): MutableSet<Role> {
        val newSet: MutableSet<Role> = mutableSetOf()
        this.forEach {
            newSet.add(it.toRole())
        }
        return newSet
    }

    fun NewTreatmentDto.toTreatment() = Treatment(
        chat = null,
        patient = patient!!.toUser(),
        doctor = null,
        conclusion = null,
        sound = null,
        symptoms = mutableSetOf()
    )

    fun MutableList<Treatment>.toTreatmentDto(): List<TreatmentDto> {
        return this.map {
            it.toTreatmentDto()
        }
    }

    fun MutableList<User>.toUserDtoList(): List<UsersDto> {
        return this.map {
            it.toUserDto()
        }
    }
}

