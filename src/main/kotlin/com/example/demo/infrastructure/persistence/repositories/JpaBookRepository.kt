package com.example.demo.infrastructure.persistence.repositories

import com.example.demo.infrastructure.persistence.entities.BookEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface JpaBookRepository : JpaRepository<BookEntity, UUID> {

    fun findByTitle(title: String): BookEntity?

    @Query("SELECT b FROM BookEntity b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    fun searchByTitle(title: String): List<BookEntity>
}
