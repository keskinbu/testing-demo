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
            throw IllegalArgumentException("title cant be less than 3 chars!")
        }
        if (title.length > 200) {
            throw IllegalArgumentException("title cant be more than 200 chars!")
        }
    }

    override fun deleteBook(id: UUID): Boolean = bookRepository.deleteBookById(id)
    override fun searchBook(searchText: String): List<Book> {
        validateSearchText(searchText)

        val result = bookRepository.searchBook(searchText)

        if(result.isEmpty()){
            throw RuntimeException("No matching result!")
        }

        return result
    }

    private fun validateSearchText(searchText: String){
        if(searchText.isEmpty()){
            throw IllegalArgumentException("Search criteria can't be empty!")
        }
        if (searchText.length < 2) {
            throw IllegalArgumentException("Search text shouldn't be less than 2 letter!")
        }
        if(searchText.length > 20){
            throw IllegalArgumentException("Search text shouldn't be more than 20 letter!")
        }
    }
}
