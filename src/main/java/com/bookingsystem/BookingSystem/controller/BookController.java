package com.bookingsystem.BookingSystem.controller;

import com.bookingsystem.BookingSystem.repository.entity.Book;
import com.bookingsystem.BookingSystem.service.impl.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")

public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        Optional<Book> bookOptional = bookService.findById(id);
        return bookOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(required = false) String title,
                                  @RequestParam(required = false) String author) {
        if (title != null) {
            return bookService.searchBooksByTitle(title);
        } else if (author != null) {
            return bookService.searchBooksByAuthor(author);
        } else {
            return bookService.getAllBooks();
        }
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(id, bookDetails);
        if (updatedBook != null) {
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/reserve/{id}")
    public ResponseEntity<String> reserveBook(@PathVariable String id) {
        boolean isReserved = bookService.reserveBook(id);
        if (isReserved) {
            return ResponseEntity.ok("Book reserved successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found or not available.");
        }
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<String> returnBook(@PathVariable String id) {
        boolean isReturned = bookService.returnBook(id);
        if (isReturned) {
            return ResponseEntity.ok("Book returned successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found or already returned.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable String id) {
        boolean isDeleted = bookService.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Book deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }
    }
}
