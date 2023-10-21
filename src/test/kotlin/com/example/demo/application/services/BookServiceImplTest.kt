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
    fun `when book title less than 3 chars then throw exception`() {
        val title = "2c"
        val book = Book(UUID.randomUUID(), title, "ty", 2001)

        val exception = assertThrows<IllegalArgumentException> {
            bookService.createBook(book)
        }

        assertEquals("Book title cant be less than 3 chars!", exception.message)
    }

    @Test
    fun `when book title bigger than 200 chars then throw exception`() {
        val title = "2c".repeat(201)
        val book = Book(UUID.randomUUID(), title, "ty", 2001)

        val exception = assertThrows<IllegalArgumentException> {
            bookService.createBook(book)
        }

        assertEquals("Book title cant be bigger than 200 chars!", exception.message)
    }

    @Test
    fun `when book search is valid then should return books`(){
        val books = listOf(
            Book(UUID.randomUUID(), "Book 1", "Murat", 2022),
            Book(UUID.randomUUID(), "Book 2", "Murat", 2023),
        )

        val searchText = "Book"
        Mockito.`when`(bookRepository.searchBook(searchText)).thenReturn(books)

        val foundBooks = bookService.searchBook(searchText);

        assertEquals(books.size, foundBooks.size)
    }

    @Test
    fun `when there is no book then should throw exception `(){
        val searchText = "thereIsNoBook"
        Mockito.`when`(bookRepository.searchBook(searchText)).thenReturn(emptyList())
        val exception = assertThrows<IllegalArgumentException> {
            bookService.searchBook(searchText)
        }
        assertEquals(exception.message, "Sonuç bulunamadı.")
    }

    @Test
    fun `when search text is less than 2 chars then should throw exception `(){
        val searchText = "c"
        Mockito.`when`(bookRepository.searchBook(searchText)).thenReturn(emptyList())
        val exception = assertThrows<IllegalArgumentException> {
            bookService.searchBook(searchText)
        }
        assertEquals(exception.message, "Arama kriteri en az 2 karakter olmadılır.")
    }

    @Test
    fun `when search text is bigger than 200 chars then should throw exception `(){
        val searchText = "c".repeat(201)
        Mockito.`when`(bookRepository.searchBook(searchText)).thenReturn(emptyList())
        val exception = assertThrows<IllegalArgumentException> {
            bookService.searchBook(searchText)
        }
        assertEquals(exception.message, "Arama kriteri en fazla 200 karakter olmadılır.")
    }

    @Test
    fun `when search text is empty then should throw exception `(){
        val searchText = ""
        Mockito.`when`(bookRepository.searchBook(searchText)).thenReturn(emptyList())
        val exception = assertThrows<IllegalArgumentException> {
            bookService.searchBook(searchText)
        }
        assertEquals(exception.message, "Lütfen bir arama kriteri girin")
    }

}
