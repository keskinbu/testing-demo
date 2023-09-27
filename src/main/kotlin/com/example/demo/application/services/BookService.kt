package com.example.demo.application.services

import com.example.demo.domain.entities.Book
import com.example.demo.domain.repositories.BookRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookService(private val bookRepository: BookRepository) {

    fun getAllBooks(): List<Book> = bookRepository.findAllBooks()

    fun getBookById(id: UUID): Book? = bookRepository.findBookById(id)

    fun createBook(book: Book): Book = bookRepository.saveBook(book)

    fun updateBook(id: UUID, book: Book): Book {
        // Burada daha fazla validasyon ve kontrol gerçekleştirilebilir
        return bookRepository.updateBook(id, book)
    }

    fun deleteBook(id: UUID): Boolean = bookRepository.deleteBookById(id)
}