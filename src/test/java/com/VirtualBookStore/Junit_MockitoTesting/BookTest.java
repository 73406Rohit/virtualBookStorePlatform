package com.VirtualBookStore.Junit_MockitoTesting;

import com.VirtualBookStore.controller.BookController;
import com.VirtualBookStore.dto.Book;
import com.VirtualBookStore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BookTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBook_Success() {
        // Arrange: Prepare a book object and the mocked response
        Book bookToAdd = new Book();
        bookToAdd.setTitle("JUnit Testing Basics");
        bookToAdd.setAuthor("Test Author");
        bookToAdd.setPrice(250.0);
        bookToAdd.setStock(50);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setTitle("JUnit Testing Basics");
        savedBook.setAuthor("Test Author");
        savedBook.setPrice(250.0);
        savedBook.setStock(50);

        when(bookService.addBook(any(Book.class))).thenReturn(savedBook);

        // Act: Call the addBook method
        ResponseEntity<Book> response = bookController.addBook(bookToAdd);

        // Assert: Verify response and status code
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1);
        assertThat(response.getBody().getTitle()).isEqualTo("JUnit Testing Basics");
    }
}
