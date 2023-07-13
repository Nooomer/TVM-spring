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
        roles = this.roles
    )

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
        this?.forEach {
            newSet.add(it.toSymptomDto())
        }
        return newSet
    }

    fun MutableSet<SymptomDto>?.toSymptom(): MutableSet<Symptom> {
        val newSet: MutableSet<Symptom> = mutableSetOf()
        this?.forEach {
            newSet.add(it.toSymptom())
        }
        return newSet
    }

    fun SymptomDto.toSymptom() = Symptom(
        symptomName = symptomsName
    )

    fun NewSymptomDto.toSymptom() = Symptom(
        symptomName = symptomName
    )

    fun MutableSet<Sound>?.toSoundDto(): MutableSet<SoundDto> {
        val newSet: MutableSet<SoundDto> = mutableSetOf()
        this?.forEach {
            newSet.add(it.toSoundDto())
        }
        return newSet
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

    fun Chat?.toChatDto(): ChatDto? {
        return if (Objects.isNull(this)) {
            null
        } else {
            ChatDto(
                id = this?.id!!,
                createdDate = createdDate!!,
                updatedDate = updatedDate,
                message = message.toMessageDto()
            )
        }
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
        roles = roles
    )

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

