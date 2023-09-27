package com.example.demo.infrastructure.persistence.entities

import com.example.demo.domain.entities.Book
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "books")
data class BookEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val author: String,

    @Column
    val publicationYear: Int?,
)

fun Book.toEntity() = BookEntity(
    id = this.id,
    title = this.title,
    author = this.author,
    publicationYear = this.publicationYear,
)

fun BookEntity.toDomain() = Book(
    id = this.id!!,
    title = this.title,
    author = this.author,
    publicationYear = this.publicationYear ?: 0,
)
