package com.example.demo.domain.repositories

import com.example.demo.domain.entities.Book
import java.util.*

interface BookRepository {
    fun findAllBooks(): List<Book>
    fun findBookById(id: UUID): Book?
    fun findBookByTitle(title: String): Book?
    fun saveBook(book: Book): Book
    fun deleteBookById(id: UUID): Boolean
}
