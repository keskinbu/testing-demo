package com.example.demo.infrastructure.api.controller

import com.example.demo.domain.entities.Book
import com.example.demo.domain.services.BookService
import com.example.demo.infrastructure.api.handlers.GlobalExceptionHandler
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.whenever
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.*

class BookControllerTest {

    private lateinit var webTestClient: WebTestClient

    @InjectMocks
    lateinit var bookController: BookController

    @Mock
    lateinit var bookService: BookService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        webTestClient = WebTestClient.bindToController(bookController)
            .controllerAdvice(GlobalExceptionHandler())
            .build()
    }

    @Test
    fun `test getAllBooks should return list of books`() {
        val books = listOf(
            Book(UUID.randomUUID(), "Title1", "Author1", 2001),
            Book(UUID.randomUUID(), "Title2", "Author2", 2002),
        )

        Mockito.`when`(bookService.getAllBooks()).thenReturn(books)

        webTestClient.get().uri("/api/books")
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[0].title").isEqualTo("Title1")
            .jsonPath("$.[1].title").isEqualTo("Title2")
    }

    @Test
    fun `test getBookById should return a book`() {
        val book = Book(UUID.randomUUID(), "Title1", "Author1", 2001)

        given(bookService.getBookById(book.id)).willReturn(book)

        webTestClient.get().uri("/api/books/${book.id}")
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Title1")
    }

    @Test
    fun `test createBook with new title should return created book`() {
        val book = Book(UUID.randomUUID(), "UniqueTitle", "Author1", 2001)
        val bookDTO = book.toDto()

        Mockito.`when`(bookService.createBook(book)).thenReturn(book)

        webTestClient.post().uri("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(ObjectMapper().writeValueAsString(bookDTO))
            .exchange()
            .expectStatus().isCreated
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.title").isEqualTo("UniqueTitle")
    }

    @Test
    fun `test createBook with duplicate title should return bad request`() {
        val book = Book(UUID.randomUUID(), "DuplicateTitle", "Author1", 2001)
        val bookDTO = book.toDto()

        whenever(bookService.createBook(any())).thenThrow(
            IllegalArgumentException("A book with the title ${book.title} already exists."),
        )

        webTestClient.post().uri("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(bookDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.message").isEqualTo("A book with the title DuplicateTitle already exists.")
    }

    @Test
    fun `test deleteBook should return no content`() {
        val bookId = UUID.randomUUID()

        Mockito.`when`(bookService.deleteBook(bookId)).thenReturn(true)

        webTestClient.delete().uri("/api/books/$bookId")
            .exchange()
            .expectStatus().isNoContent
    }
}
