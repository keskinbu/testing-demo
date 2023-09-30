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

class BookServiceImplTest {

    @InjectMocks
    lateinit var bookService: BookServiceImpl

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
    fun `test createBook with less than 3 chars title then throw exception`() {
        val title = "ab"
        val book = Book(UUID.randomUUID(), title, "author", 2002)

        val exception = assertThrows<IllegalArgumentException> {
            bookService.createBook(book)
        }

        assertEquals("title cant be less than 3 chars!", exception.message)
    }

    @Test
    fun `test createBook with more than 200 chars title then throw exception`() {
        val title = "ab".repeat(200)
        val book = Book(UUID.randomUUID(), title, "author", 2002)

        val exception = assertThrows<IllegalArgumentException> {
            bookService.createBook(book)
        }

        assertEquals("title cant be more than 200 chars!", exception.message)
    }

    @Test
    fun `test createBook with new title should return saved book`() {
        val book = Book(UUID.randomUUID(), "UniqueTitle", "Author1", 2001)

        Mockito.`when`(bookRepository.findBookByTitle(book.title)).thenReturn(null)
        Mockito.`when`(bookRepository.saveBook(book)).thenReturn(book)

        val result = bookService.createBook(book)
        assertEquals(book, result)
    }

    @Test
    fun `test createBook with duplicate title should throw exception`() {
        val book = Book(UUID.randomUUID(), "DuplicateTitle", "Author1", 2001)

        Mockito.`when`(bookRepository.findBookByTitle(book.title)).thenReturn(book)

        val exception = assertThrows<IllegalArgumentException> {
            bookService.createBook(book)
        }

        assertEquals("A book with the title DuplicateTitle already exists.", exception.message)
    }

    @Test
    fun `test searchBook with text lest than 2 letter should throw exception`(){
        val searchText = ""

        val exception = assertThrows<IllegalArgumentException> {
            bookService.searchBook(searchText)
        }

        assertEquals(exception.message, "Search text shouldn't be less than 2 letter!")

    }

    @Test
    fun `test searchBook with text more than 20 letter should throw exception`(){
        val searchText = "a".repeat(21)

        val exception = assertThrows<IllegalArgumentException> {
            bookService.searchBook(searchText)
        }

        assertEquals(exception.message, "Search text shouldn't be more than 20 letter!")
    }

}
