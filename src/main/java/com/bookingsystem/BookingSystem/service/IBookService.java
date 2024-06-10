package com.bookingsystem.BookingSystem.service;

import com.bookingsystem.BookingSystem.repository.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IBookService {
    List<Book> getAllBooks();
    Optional<Book> findById(String id);
    List<Book> searchBooksByTitle(String title);
    List<Book> searchBooksByAuthor(String author);
    void save(Book book);
    Book updateBook(String id, Book bookDetails);
    boolean deleteById(String id);
}
