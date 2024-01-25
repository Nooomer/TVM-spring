package com.nooomer.tvmspring.services

import com.nooomer.tvmspring.db.models.Symptom
import com.nooomer.tvmspring.db.repositories.SymptomRepository
import com.nooomer.tvmspring.db.repositories.TreatmentRepository
import com.nooomer.tvmspring.dto.NewTreatmentDto
import com.nooomer.tvmspring.dto.TreatmentDto
import com.nooomer.tvmspring.exceptions.SymptomNotFound
import com.nooomer.tvmspring.exceptions.TreatmentNotFoundException
import com.nooomer.tvmspring.services.helpers.Converter.toTreatment
import com.nooomer.tvmspring.services.helpers.Converter.toTreatmentDto
import org.springframework.stereotype.Service
import java.util.*

@Service
class TreatmentService(
    val treatmentRepository: TreatmentRepository,
    val userService: UserService,
    val symptomRepository: SymptomRepository
) {

    fun getAllTreatment(): List<TreatmentDto> {
        val data = treatmentRepository.findAll()
        return if (data.isEmpty()) {
            throw TreatmentNotFoundException("Treatment not found")
        } else {
            data.toTreatmentDto()
        }
    }

    fun getTreatmentById(treatmentId: UUID): TreatmentDto {
       return treatmentRepository.findById(treatmentId).orElseThrow{
            TreatmentNotFoundException("Treatmetn with id=${treatmentId} not found")
        }.toTreatmentDto()
    }
    fun getAllTreatmentForUser(): List<TreatmentDto> {
        val user = userService.getCurrentUserDto()
        var data = listOf<TreatmentDto>()
        val role = user.roles.map { it.name }[0]
        when(role){
            "ROLE_DOCTOR" -> {
                data = treatmentRepository.findByDoctorId(user.id).toTreatmentDto()
            }
            "ROLE_PATIENT" -> {
                data = treatmentRepository.findByPatientId(user.id).toTreatmentDto()
            }
            "ROLE_ADMIN" -> {
                data = getAllTreatment()
            }
        }
        return if (data.isEmpty()) {
            if(role == "ROLE_DOCTOR") {
                throw TreatmentNotFoundException("Treatment for doctor with id=${user.id} not found")
            }
            else{
                throw TreatmentNotFoundException("Treatment for patient with id=${user.id} not found")
            }
        } else {
           data
        }
    }

    private fun createNewEmptyChat(): Chat {
        return chatsRepository.save(Chat(null))
    }

    fun addTreatment(treatmentDto: NewTreatmentDto): TreatmentDto {
        val currentUser = userService.getCurrentUser()
        val newTreatment = treatmentDto.toTreatment()
        val usedSymptoms: MutableSet<Symptom> = mutableSetOf()
        treatmentDto.symptomIds.forEach {
            usedSymptoms.add(symptomRepository.findById(it).orElseThrow {
                throw SymptomNotFound("Symptom with id=${it} not found")
            })
        }
        with(newTreatment) {
            symptoms = usedSymptoms
            patient = currentUser
            chat = createNewEmptyChat()
        }
        return treatmentRepository.save(newTreatment).toTreatmentDto()
    }


    fun setDoctor(treatmentId: UUID): TreatmentDto {
        val currentUser = userService.getCurrentUser()
        val treatment = treatmentRepository.findById(treatmentId).orElseThrow {
            TreatmentNotFoundException("Treatment with id=${treatmentId} not found")
        }
        treatment.doctor = currentUser
        val newTreatment = treatmentRepository.save(treatment)
        return newTreatment.toTreatmentDto()
    }
}