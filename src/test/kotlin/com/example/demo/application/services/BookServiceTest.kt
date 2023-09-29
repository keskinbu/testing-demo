package com.example.demo.application.services

import com.example.demo.domain.entities.Book
import com.example.demo.domain.repositories.BookRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class BookServiceTest {

    @InjectMocks
    lateinit var bookService: BookService

    @Mock
    lateinit var bookRepository: BookRepository

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test getAllBooks should return list of books`() {
        val books = listOf(
            Book(UUID.randomUUID(), "Title1", "Author1", 2001),
            Book(UUID.randomUUID(), "Title2", "Author2", 2002),
        )

        Mockito.`when`(bookRepository.findAllBooks()).thenReturn(books)

        val result = bookService.getAllBooks()
        assertEquals(2, result.size)
        assertEquals(books, result)
    }

    @Test
    fun `test getBookById should return a book`() {
        val book = Book(UUID.randomUUID(), "Title1", "Author1", 2001)

        Mockito.`when`(bookRepository.findBookById(book.id)).thenReturn(book)

        val result = bookService.getBookById(book.id)
        assertEquals(book, result)
    }

    @Test
    fun `test createBook with new title should return saved book`() {
        val book = Book(UUID.randomUUID(), "UniqueTitle", "Author1", 2001)

        // Assume there's no other book with the same title
        Mockito.`when`(bookRepository.findBookByTitle(book.title)).thenReturn(book)
        Mockito.`when`(bookRepository.saveBook(book)).thenReturn(book)

        val result = bookService.createBook(book)
        assertEquals(book, result)
    }

    @Test
    fun `test createBook with duplicate title should throw exception`() {
        val book = Book(UUID.randomUUID(), "DuplicateTitle", "Author1", 2001)

        // Assume there's another book with the same title
        Mockito.`when`(bookRepository.findBookByTitle(book.title)).thenReturn(book)

        val exception = assertThrows<IllegalArgumentException> {
            bookService.createBook(book)
        }

        assertEquals("A book with the title DuplicateTitle already exists.", exception.message)
    }
}
