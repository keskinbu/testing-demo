package com.example.demo.infrastructure.persistence.repositories

import com.example.demo.domain.entities.Book
import com.example.demo.infrastructure.persistence.entities.toDomain
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

    @Test
    fun `when book name contains search text then should return matched books`() {
        val allBooks = listOf(
            Book(UUID.randomUUID(), "Good Book 1", "Perfect Author", 1999),
            Book(UUID.randomUUID(), "Good Book 2", "Perfect Author", 2001),
            Book(UUID.randomUUID(), "Bad Book 1", "Bad Author", 1999),
        ).map { x -> x.toEntity() }

        val searchText = "Good"
        val expectedBooks = listOf(
            allBooks[0].toDomain(),
            allBooks[1].toDomain())

        Mockito.`when`(jpaRepo.findAll()).thenReturn(allBooks);

        val foundBooks = bookRepository.searchBook(searchText);

        assertEquals(expectedBooks.size, foundBooks.size)
        assertEquals(expectedBooks.get(0).id, foundBooks.get(0).id)
        assertEquals(expectedBooks.get(1).id, foundBooks.get(1).id)
    }

    @Test
    fun `when search text is randomly capitalized then should return matched books`() {
        val allBooks = listOf(
            Book(UUID.randomUUID(), "Good Book 1", "Perfect Author", 1999),
            Book(UUID.randomUUID(), "Good Book 2", "Perfect Author", 2001),
            Book(UUID.randomUUID(), "Bad Book 1", "Bad Author", 1999),
        ).map { x -> x.toEntity() }

        val searchText = "goOD"
        val expectedBooks = listOf(
            allBooks[0].toDomain(),
            allBooks[1].toDomain())

        Mockito.`when`(jpaRepo.findAll()).thenReturn(allBooks);

        val foundBooks = bookRepository.searchBook(searchText);

        assertEquals(expectedBooks.size, foundBooks.size)
        assertEquals(expectedBooks.get(0).id, foundBooks.get(0).id)
        assertEquals(expectedBooks.get(1).id, foundBooks.get(1).id)
    }

    @Test
    fun `when book title or book author contains search text then should return all matched books`() {
        val allBooks = listOf(
            Book(UUID.randomUUID(), "Harry Potter and The Sorcerer's Stone", "J. K. Rowling", 1997),
            Book(UUID.randomUUID(), "Harry Potter and Prisoner of Azkaban", "J. K. Rowling", 1999),
            Book(UUID.randomUUID(), "The Invisible Wall: A Love Story That Broke Barriers", "Harry Bernstein", 2006),
            Book(UUID.randomUUID(), "Unfortunate Book", "Good Author", 1999),
        ).map { x -> x.toEntity() }

        val searchText = "Harry"
        val expectedBooks = listOf(
            allBooks[0].toDomain(),
            allBooks[1].toDomain(),
            allBooks[2].toDomain())

        Mockito.`when`(jpaRepo.findAll()).thenReturn(allBooks);

        val foundBooks = bookRepository.searchBook(searchText);

        assertEquals(expectedBooks.size, foundBooks.size)
        assertEquals(expectedBooks[0].id, foundBooks[0].id)
        assertEquals(expectedBooks[1].id, foundBooks[1].id)
        assertEquals(expectedBooks[2].id, foundBooks[2].id)
    }

    @Test
    fun `when book name or author does not contains search text then should return empty list`() {
        val allBooks = listOf(
            Book(UUID.randomUUID(), "Good Book 1", "Perfect Author", 1999),
            Book(UUID.randomUUID(), "Good Book 2", "Perfect Author", 2001),
            Book(UUID.randomUUID(), "Bad Book 1", "Bad Author", 1999),
        ).map { x -> x.toEntity() }

        val searchText = "randomSearchText"
        Mockito.`when`(jpaRepo.findAll()).thenReturn(allBooks);
        val foundBooks = bookRepository.searchBook(searchText);

        assertEquals(0, foundBooks.size)
    }

}
