package com.example.demo.infrastructure.persistence.repositories

import com.example.demo.infrastructure.persistence.entities.BookEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaBookRepository : JpaRepository<BookEntity, UUID>
