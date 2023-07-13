package com.nooomer.tvmspring.controllers

import com.nooomer.tvmspring.dto.NewSymptomDto
import com.nooomer.tvmspring.dto.SymptomDto
import com.nooomer.tvmspring.services.SymptomService
import com.nooomer.tvmspring.services.helpers.interfaces.access.annotation.IsAdmin
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/symptoms")
class SymptomsController(
    val symptomService: SymptomService,
) {

    @PostMapping
    @IsAdmin
    fun addSymptom(@RequestBody newSymptomDto: NewSymptomDto): ResponseEntity<SymptomDto> {
        return ResponseEntity.ok(symptomService.addSymptom(newSymptomDto))
    }

    @GetMapping
    fun getAllSymptoms(): ResponseEntity<List<SymptomDto>> {
        return ResponseEntity.ok(symptomService.getAllSymptoms())
    }
}