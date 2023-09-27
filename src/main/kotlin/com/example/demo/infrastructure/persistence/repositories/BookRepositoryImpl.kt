package com.example.demo.infrastructure.persistence.repositories

import com.example.demo.domain.entities.Book
import com.example.demo.domain.repositories.BookRepository
import com.example.demo.infrastructure.persistence.entities.toDomain
import com.example.demo.infrastructure.persistence.entities.toEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class BookRepositoryImpl(private val jpaRepo: JpaBookRepository) : BookRepository {

    override fun findAllBooks(): List<Book> = jpaRepo.findAll().map { it.toDomain() }

    override fun findBookById(id: UUID): Book? = jpaRepo.findById(id).orElseGet { null }?.toDomain()

    override fun saveBook(book: Book): Book = jpaRepo.save(book.toEntity()).toDomain()

    override fun updateBook(id: UUID, book: Book): Book {
        if (jpaRepo.existsById(id)) {
            return jpaRepo.save(book.copy(id = id).toEntity()).toDomain()
        }
        throw IllegalArgumentException("Book Not Found")
    }

    override fun deleteBookById(id: UUID): Boolean {
        if (jpaRepo.existsById(id)) {
            jpaRepo.deleteById(id)
            return true
        }
        return false
    }
}
