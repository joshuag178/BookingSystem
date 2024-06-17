package com.bookingsystem.BookingSystem.service;

import com.bookingsystem.BookingSystem.repository.BookRepository;
import com.bookingsystem.BookingSystem.repository.entity.Book;
import com.bookingsystem.BookingSystem.service.impl.BookService;
import com.bookingsystem.BookingSystem.service.IBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllBooks_shouldReturnBookList() {
        // Arrange
        Book book1 = new Book("1", "Title 1", "Author 1", "Editorial 1", "October 17, 1995", true, "Available");
        Book book2 = new Book("2", "Title 2", "Author 2", "Editorial 2", "March 20, 1975", true, "Available");
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Act
        List<Book> books = bookService.getAllBooks();

        // Assert
        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void findById_shouldReturnBook() {
        // Arrange
        Book book = new Book("1", "Title 1", "Author 1", "Editorial 1", "October 17, 1995", true, "Available");
        when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));

        // Act
        Optional<Book> foundBook = bookService.findById("1");

        // Assert
        assertTrue(foundBook.isPresent());
        assertEquals("Title 1", foundBook.get().getTitle());
        verify(bookRepository, times(1)).findById(anyString());
    }

    @Test
    public void searchBooksByTitle_shouldReturnBookList() {
        // Arrange
        Book book1 = new Book("1", "Title 1", "Author 1", "Editorial 1", "October 17, 1995", true, "Available");
        when(bookRepository.findByTitleContaining(anyString())).thenReturn(List.of(book1));

        // Act
        List<Book> books = bookService.searchBooksByTitle("Title");

        // Assert
        assertEquals(1, books.size());
        assertEquals("Title 1", books.get(0).getTitle());
        verify(bookRepository, times(1)).findByTitleContaining(anyString());
    }

    @Test
    public void searchBooksByAuthor_shouldReturnBookList() {
        // Arrange
        Book book = new Book("1", "Title 1", "Author 1", "Editorial 1", "October 17, 1995", true, "Available");
        when(bookRepository.findByAuthorContaining(anyString())).thenReturn(List.of(book));

        // Act
        List<Book> books = bookService.searchBooksByAuthor("Author");

        // Assert
        assertEquals(1, books.size());
        assertEquals("Author 1", books.get(0).getAuthor());
        verify(bookRepository, times(1)).findByAuthorContaining(anyString());
    }

    @Test
    public void save_shouldSaveBook() {
        // Arrange
        Book book = new Book("1", "Title 1", "Author 1", "Editorial 1", "October 17, 1995", true, "Available");
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        bookService.save(book);

        // Assert
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void updateBook_shouldReturnUpdatedBook() {
        // Arrange
        Book existingBook = new Book("1", "Title 1", "Author 1", "Editorial 1", "October 17, 1995", true, "Available");
        Book bookDetails = new Book("1", "UpdatedTitle", "UpdatedAuthor", "UpdatedEditorial", "October 17, 1995", true, "Available");
        when(bookRepository.findById(anyString())).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(bookDetails);

        // Act
        Book updatedBook = bookService.updateBook("1", bookDetails);

        // Assert
        assertNotNull(updatedBook);
        assertEquals("UpdatedTitle", updatedBook.getTitle());
        assertEquals("UpdatedAuthor", updatedBook.getAuthor());
        verify(bookRepository, times(1)).findById(anyString());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void deleteById_shouldReturnTrue() {
        // Arrange
        Book book = new Book("1", "Title 1", "Author 1", "Editorial 1", "October 17, 1995", true, "Available");
        when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));

        // Act
        boolean deleted = bookService.deleteById("1");

        // Assert
        assertTrue(deleted);
        verify(bookRepository, times(1)).findById(anyString());
        verify(bookRepository, times(1)).deleteById(anyString());
    }

    @Test
    public void deleteById_shouldReturnFalseWhenBookNotFound() {
        // Arrange
        when(bookRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        boolean deleted = bookService.deleteById("1");

        // Assert
        assertFalse(deleted);
        verify(bookRepository, times(1)).findById(anyString());
        verify(bookRepository, times(0)).deleteById(anyString());
    }
}
