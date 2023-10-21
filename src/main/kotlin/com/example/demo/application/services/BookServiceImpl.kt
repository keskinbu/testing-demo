package com.example.demo.application.services

import com.example.demo.domain.entities.Book
import com.example.demo.domain.repositories.BookRepository
import com.example.demo.domain.services.BookService
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookServiceImpl(private val bookRepository: BookRepository) : BookService {

    override fun getAllBooks(): List<Book> = bookRepository.findAllBooks()

    override fun getBookById(id: UUID): Book? = bookRepository.findBookById(id)
    override fun createBook(book: Book): Book {
        validateTitle(book.title)
        val existingBook = bookRepository.findBookByTitle(book.title)
        if (existingBook != null) {
            throw IllegalArgumentException("A book with the title ${book.title} already exists.")
        }
        return bookRepository.saveBook(book)
    }

    private fun validateTitle(title: String) {
        if (title.length < 3) {
            throw IllegalArgumentException("Book title cant be less than 3 chars!")
        }
        if (title.length > 200) {
            throw IllegalArgumentException("Book title cant be bigger than 200 chars!")
        }
    }

    override fun deleteBook(id: UUID): Boolean = bookRepository.deleteBookById(id)
    override fun searchBook(searchText: String): List<Book> {
        validateSearchText(searchText)

        val books = bookRepository.searchBook(searchText)

        if (books.isEmpty()) {
            throw IllegalArgumentException("Sonuç bulunamadı.")
        }

        return books;
    }

    private fun validateSearchText(searchText: String) {
        if (searchText.isEmpty()) {
            throw IllegalArgumentException("Lütfen bir arama kriteri girin")
        } else if (searchText.length < 2) {
            throw IllegalArgumentException("Arama kriteri en az 2 karakter olmadılır.")
        } else if (searchText.length > 100) {
            throw IllegalArgumentException("Arama kriteri en fazla 200 karakter olmadılır.")
        }
    }
}
