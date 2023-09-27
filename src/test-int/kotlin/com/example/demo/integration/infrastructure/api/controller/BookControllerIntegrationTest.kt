package com.example.demo.integration.infrastructure.api.controller

import com.example.demo.application.dtos.BookDTO
import com.example.demo.domain.entities.Book
import com.example.demo.domain.repositories.BookRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var bookRepository: BookRepository

    val objectMapper = ObjectMapper()

    @Test
    fun `test getAllBooks should return list of books`() {
        mockMvc.perform(
            get("/api/books")
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `test getBookById should return a book`() {
        val book = Book(UUID.randomUUID(), "Title1", "Author1", 2001)
        val savedBook = bookRepository.saveBook(book)

        mockMvc.perform(
            get("/api/books/${savedBook.id}")
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `test createBook should return created book`() {
        val bookDto = BookDTO(UUID.randomUUID(), "Title2", "Author2", 2002)

        mockMvc.perform(
            post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)),
        )
            .andExpect(status().isCreated)
    }

    @Test
    fun `test deleteBook should delete a book`() {
        val book = Book(UUID.randomUUID(), "Title3", "Author3", 2003)
        val savedBook = bookRepository.saveBook(book)

        mockMvc.perform(
            delete("/api/books/${savedBook.id}")
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpect(status().isNoContent)
    }
}
