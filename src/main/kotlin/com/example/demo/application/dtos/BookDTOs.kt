package com.example.demo.application.dtos

import java.util.*

// Kullanıcıdan kitap oluşturmak için alınan bilgileri temsil eden DTO
data class CreateBookRequest(
    val title: String,
    val author: String,
    val publicationYear: Int,
)

// Kullanıcıdan kitap güncellemek için alınan bilgileri temsil eden DTO
data class UpdateBookRequest(
    val title: String? = null,
    val author: String? = null,
    val publicationYear: Int? = null,
)

// API yanıtı olarak döndürülen kitap bilgilerini temsil eden DTO
data class BookResponse(
    val id: UUID,
    val title: String,
    val author: String,
    val publicationYear: Int,
)
