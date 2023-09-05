package com.nooomer.tvmspring.services.helpers.interfaces.access.annotation

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
annotation class IsAdminOrPatient
