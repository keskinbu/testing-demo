package com.example.demo.application.dtos

import java.util.*

data class BookDTO(
    val id: UUID,
    val title: String,
    val author: String,
    val publicationYear: Int,
)
