package com.example.demo.domain.entities

import java.util.*

data class Book(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val author: String,
    val publicationYear: Int,
)
