package com.example.demo.infrastructure.api.dtos

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ErrorDTO(val message: String, val status: HttpStatus, val timestamp: LocalDateTime)
