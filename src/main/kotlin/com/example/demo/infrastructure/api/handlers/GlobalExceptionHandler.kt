package com.example.demo.infrastructure.api.handlers

import com.example.demo.infrastructure.api.dtos.ErrorDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [IllegalArgumentException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(exception: IllegalArgumentException): ResponseEntity<ErrorDTO> {
        val error = ErrorDTO(
            message = exception.message ?: "Invalid request",
            status = HttpStatus.BAD_REQUEST,
            timestamp = LocalDateTime.now(),
        )
        println("Handling IllegalArgumentException: ${exception.message}")
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }
}
