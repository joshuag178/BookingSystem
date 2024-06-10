package com.bookingsystem.BookingSystem.controller;

import com.bookingsystem.BookingSystem.config.TestSecurityConfig;
import com.bookingsystem.BookingSystem.repository.entity.Book;
import com.bookingsystem.BookingSystem.service.impl.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@Import(TestSecurityConfig.class)

public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllUser_shouldReturnListOfBook() throws Exception{
        //Arrange
        List<Book> books = Arrays.asList(
                new Book("1", "Book 1", "Joan", "Editorial 1", "October 15, 1955", true, "Available"),
                new Book("2", "Book 2", "Ronald", "Editorial 2", "April 04, 1976", true, "Available")
        );
        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }

    @Test
    void getBookById_shouldReturnBook() throws Exception {
        Book book = new Book("1", "Book 1", "Joan", "Editorial 1", "October 15, 1955", true, "Available");
        when(bookService.findById("1")).thenReturn(Optional.of(book));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book 1"));
    }

    @Test
    void getBookById_shouldReturnNotFound() throws Exception {
        when(bookService.findById("1")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void searchBooksByTitle_shouldReturnBooks() throws Exception {
        List<Book> books = List.of(
                new Book("1", "Book 1", "Joan", "Editorial 1", "October 15, 1955", true, "Available")
        );
        when(bookService.searchBooksByTitle("Book 1")).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/search")
                        .param("title", "Book 1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1));
    }

    @Test
    void searchBooksByAuthor_shouldReturnBooks() throws Exception {
        List<Book> books = List.of(
                new Book("1", "Book 1", "Joan", "Editorial 1", "October 15, 1955", true, "Available")
        );
        when(bookService.searchBooksByAuthor("Joan")).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/search")
                        .param("author", "Joan"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1));
    }

    @Test
    void createBook_shouldReturnCreatedBook() throws Exception {
        Book book = new Book("1", "Book 1", "Joan", "Editorial 1", "October 15, 1955", true, "Available");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book 1"));

        verify(bookService, times(1)).save(book);
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() throws Exception {
        // Arrange
        String userId = "1";
        Book updatedBookDetails = new Book("1", "Book 1", "Joan", "Editorial 1", "October 15, 1955", true, "Available");
        Book updatedBook = new Book(userId, "Book 1", "Ronald", "Editorial 2", "October 15, 1972", true, "Available");

        when(bookService.updateBook(userId, updatedBookDetails)).thenReturn(updatedBook);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBookDetails)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(updatedBook.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(updatedBook.getAuthor()));

        verify(bookService, times(1)).updateBook(userId, updatedBookDetails);
    }

    @Test
    void updateBook_shouldReturnNotFound() throws Exception {
        when(bookService.updateBook(anyString(), any(Book.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Book())))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteBook_shouldReturnOk() throws Exception {
        when(bookService.deleteById("1")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/books/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Book deleted successfully."));

        verify(bookService, times(1)).deleteById("1");
    }

    @Test
    void deleteBook_shouldReturnNotFound() throws Exception {
        when(bookService.deleteById("1")).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/books/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Book not found."));
    }
}
