package com.nooomer.tvmspring.services

import com.nooomer.tvmspring.db.repositories.SymptomRepository
import com.nooomer.tvmspring.dto.NewSymptomDto
import com.nooomer.tvmspring.dto.SymptomDto
import com.nooomer.tvmspring.services.helpers.Converter.toSymptom
import com.nooomer.tvmspring.services.helpers.Converter.toSymptomDto
import org.springframework.stereotype.Service

@Service
class SymptomService(
    private val symptomRepository: SymptomRepository
) {

    fun addSymptom(newSymptomDto: NewSymptomDto): SymptomDto {
        return symptomRepository.save(newSymptomDto.toSymptom()).toSymptomDto()
    }

    fun getAllSymptoms(): List<SymptomDto> {
        return symptomRepository.findAll().toMutableList().toSymptomDto()
    }
}