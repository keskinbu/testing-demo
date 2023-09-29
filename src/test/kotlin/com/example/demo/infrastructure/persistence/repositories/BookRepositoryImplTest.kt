package com.example.demo.infrastructure.persistence.repositories

import com.example.demo.domain.entities.Book
import com.example.demo.infrastructure.persistence.entities.toEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class BookRepositoryImplTest {

    @InjectMocks
    lateinit var bookRepository: BookRepositoryImpl

    @Mock
    lateinit var jpaRepo: JpaBookRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test findAllBooks returns books`() {
        val bookEntity = Book(UUID.randomUUID(), "Title", "Author", 2020).toEntity()
        Mockito.`when`(jpaRepo.findAll()).thenReturn(listOf(bookEntity))

        val books = bookRepository.findAllBooks()

        assertEquals(1, books.size)
        assertEquals("Title", books[0].title)
    }

    @Test
    fun `test findBookById returns book`() {
        val id = UUID.randomUUID()
        val bookEntity = Book(id, "Title", "Author", 2020).toEntity()
        Mockito.`when`(jpaRepo.findById(id)).thenReturn(Optional.of(bookEntity))

        val book = bookRepository.findBookById(id)

        assertNotNull(book)
        assertEquals("Title", book?.title)
    }

    @Test
    fun `test findBookByTitle returns book`() {
        val id = UUID.randomUUID()
        val title = "title"
        val bookEntity = Book(id, title, "Author", 2020).toEntity()
        Mockito.`when`(jpaRepo.findByTitle(title)).thenReturn(bookEntity)

        val book = bookRepository.findBookByTitle(title)

        assertNotNull(book)
        assertEquals(title, book?.title)
    }

    @Test
    fun `test saveBook returns saved book`() {
        val book = Book(UUID.randomUUID(), "Title", "Author", 2020)
        val savedBookEntity = Book(UUID.randomUUID(), "Title", "Author", 2020).toEntity()
        Mockito.`when`(jpaRepo.save(Mockito.any())).thenReturn(savedBookEntity)

        val savedBook = bookRepository.saveBook(book)

        assertNotNull(savedBook.id)
        assertEquals("Title", savedBook.title)
    }

    @Test
    fun `test deleteBookById returns true`() {
        val id = UUID.randomUUID()
        Mockito.`when`(jpaRepo.existsById(id)).thenReturn(true)

        val result = bookRepository.deleteBookById(id)

        assertTrue(result)
    }
}
