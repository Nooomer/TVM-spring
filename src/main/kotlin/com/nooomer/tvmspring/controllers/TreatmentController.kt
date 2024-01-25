package com.nooomer.tvmspring.controllers

import com.nooomer.tvmspring.dto.NewTreatmentDto
import com.nooomer.tvmspring.dto.TreatmentDto
import com.nooomer.tvmspring.services.TreatmentService
import com.nooomer.tvmspring.services.helpers.interfaces.access.annotation.IsAdmin
import com.nooomer.tvmspring.services.helpers.interfaces.access.annotation.IsAdminOrDoctor
import com.nooomer.tvmspring.services.helpers.interfaces.access.annotation.IsAdminOrPatient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

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

    @GetMapping
    fun getAllTreatmentForUser(): ResponseEntity<List<TreatmentDto>> {
        return ResponseEntity.ok(treatmentService.getAllTreatmentForUser())
    }

    @PostMapping
    @IsAdminOrPatient
    fun addTreatment(@RequestBody treatmentDto: NewTreatmentDto): ResponseEntity<TreatmentDto> {
        return ResponseEntity.ok(treatmentService.addTreatment(treatmentDto))
    }

    @PatchMapping
    @IsAdminOrDoctor
    fun assignDoctor(@RequestBody treatmentId: UUID): ResponseEntity<TreatmentDto> {
        return ResponseEntity.ok(treatmentService.setDoctor(treatmentId))
    }

}