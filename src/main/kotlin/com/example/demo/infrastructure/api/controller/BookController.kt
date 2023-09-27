package com.example.demo.infrastructure.api.controller

import com.example.demo.application.dtos.BookResponse
import com.example.demo.application.services.BookService
import com.example.demo.domain.entities.Book
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/books")
class BookController(private val bookService: BookService) {

    @GetMapping
    fun getAllBooks(): List<BookResponse> = bookService.getAllBooks().map { it.toDto() }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: UUID): BookResponse? = bookService.getBookById(id)?.toDto()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@RequestBody bookDto: BookResponse): BookResponse =
        bookService.createBook(bookDto.toDomain()).toDto()

    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: UUID, @RequestBody bookDto: BookResponse): BookResponse =
        bookService.updateBook(id, bookDto.toDomain()).toDto()

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable id: UUID) = bookService.deleteBook(id)
}

// Burada, Book ve BookDto arasında dönüşüm yapmak için basit yardımcı fonksiyonlar ekleyebiliriz.
// Daha kompleks uygulamalarda bu dönüşümler bir "mapper" sınıfı veya servisi tarafından yönetilir.

fun Book.toDto() = BookResponse(
    id = this.id,
    title = this.title,
    author = this.author,
    publicationYear = this.publicationYear,
)

fun BookResponse.toDomain() = Book(
    id = this.id,
    title = this.title,
    author = this.author,
    publicationYear = this.publicationYear,
)
