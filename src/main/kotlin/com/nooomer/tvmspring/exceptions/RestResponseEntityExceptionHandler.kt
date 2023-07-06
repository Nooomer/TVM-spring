package com.nooomer.tvmspring.exceptions

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(value = [UserNotFoundException::class])
    protected fun handleUserNotFound(ex: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        val status: HttpStatus = HttpStatus.NOT_FOUND
        val error = buildErrorDto(status, ex, request)
        return handleExceptionInternal(ex, error, HttpHeaders(), status, request)
    }

    @ExceptionHandler(value = [NotAuthorizeException::class])
    protected fun handleUserNotAuth(ex: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        val status: HttpStatus = HttpStatus.UNAUTHORIZED
        val error = buildErrorDto(status, ex, request)
        log.error(ex.message, ex)
        return handleExceptionInternal(ex, error, HttpHeaders(), status, request)
    }
    @ExceptionHandler(value = [AlreadyAuthorizeException::class])
    protected fun handleUserAlreadyAuth(ex: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        val status: HttpStatus = HttpStatus.CONFLICT
        val error = buildErrorDto(status, ex, request)
        log.error(ex.message, ex)
        return handleExceptionInternal(ex, error, HttpHeaders(), status, request)
    }

    private fun buildErrorDto(status: HttpStatus, ex: RuntimeException, request: WebRequest, ): ErrorDto {
        return ErrorDto(LocalDateTime.now().toString(),
            status.value(),
            ex.javaClass.simpleName,
            ex.message!!,
            request.getDescription(false).split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
        )
    }
}