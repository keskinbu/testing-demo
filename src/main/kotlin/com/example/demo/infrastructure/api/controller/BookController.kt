package com.example.demo.infrastructure.api.controller

import com.example.demo.application.dtos.BookDTO
import com.example.demo.application.services.BookService
import com.example.demo.domain.entities.Book
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/books")
class BookController(private val bookService: BookService) {

    @GetMapping
    fun getAllBooks(): List<BookDTO> = bookService.getAllBooks().map { it.toDto() }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: UUID): BookDTO? = bookService.getBookById(id)?.toDto()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@RequestBody bookDto: BookDTO): BookDTO = bookService.createBook(bookDto.toDomain()).toDto()

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable id: UUID) = bookService.deleteBook(id)
}

fun Book.toDto() = BookDTO(
    id = this.id,
    title = this.title,
    author = this.author,
    publicationYear = this.publicationYear,
)

fun BookDTO.toDomain() = Book(
    id = this.id,
    title = this.title,
    author = this.author,
    publicationYear = this.publicationYear,
)
