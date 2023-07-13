package com.nooomer.tvmspring.services.helpers.interfaces.access.annotation

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasRole('PATIENT')")
annotation class IsPatient
