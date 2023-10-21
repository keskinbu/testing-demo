package com.example.demo.domain.services

import com.example.demo.domain.entities.Book
import java.util.*

interface BookService {

    fun getAllBooks(): List<Book>

    fun getBookById(id: UUID): Book?

    fun createBook(book: Book): Book

    fun deleteBook(id: UUID): Boolean

    fun searchBook(searchText: String): List<Book>
}
