package com.example.demo.domain.repositories

import com.example.demo.domain.entities.Book
import java.util.*

interface BookRepository {

    fun findAllBooks(): List<Book>

    fun findBookById(id: UUID): Book?

    fun saveBook(book: Book): Book

    fun updateBook(id: UUID, book: Book): Book

    fun deleteBookById(id: UUID): Boolean
}
