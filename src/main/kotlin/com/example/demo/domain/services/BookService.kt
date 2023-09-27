package com.example.demo.domain.services

import com.example.demo.domain.entities.Book
import java.util.*

interface BookService {

    fun getAllBooks(): List<Book>

    fun getBookById(bookId: UUID): Book?

    fun addBook(book: Book): Book

    fun updateBook(bookId: UUID, updatedBook: Book): Book?

    fun deleteBook(bookId: UUID): Boolean
}
