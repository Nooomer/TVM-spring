package com.nooomer.tvmspring.controllers

import com.nooomer.tvmspring.dto.NewTreatmentDto
import com.nooomer.tvmspring.dto.TreatmentDto
import com.nooomer.tvmspring.services.TreatmentService
import com.nooomer.tvmspring.services.helpers.interfaces.access.annotation.IsAdmin
import com.nooomer.tvmspring.services.helpers.interfaces.access.annotation.IsPatient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/treatment")
class TreatmentController(
    val treatmentService: TreatmentService,
) {

    @GetMapping("/all")
    @IsAdmin
    fun getAllTreatment(): ResponseEntity<List<TreatmentDto>> {
        return ResponseEntity.ok(treatmentService.getAllTreatment())
    }

    @GetMapping()
    fun getAllTreatmentForUser(): ResponseEntity<List<TreatmentDto>> {
        return ResponseEntity.ok(treatmentService.getAllTreatmentForUser())
    }

    @PostMapping
    @IsAdmin
    @IsPatient
    fun addTreatment(@RequestBody treatmentDto: NewTreatmentDto): ResponseEntity<TreatmentDto> {
        return ResponseEntity.ok(treatmentService.addTreatment(treatmentDto))
    }

}