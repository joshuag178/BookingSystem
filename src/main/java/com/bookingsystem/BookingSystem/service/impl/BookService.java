package com.bookingsystem.BookingSystem.service.impl;

import com.bookingsystem.BookingSystem.repository.BookRepository;
import com.bookingsystem.BookingSystem.repository.entity.Book;
import com.bookingsystem.BookingSystem.service.IBookService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookService implements IBookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    @Override
    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContaining(author);
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Book updateBook(String id, Book bookDetails) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            if (bookDetails.getTitle() != null) {
                book.setTitle(bookDetails.getTitle());
            }
            if (bookDetails.getAuthor() != null) {
                book.setAuthor(bookDetails.getAuthor());
            }
            if (bookDetails.getEditorial() != null) {
                book.setEditorial(bookDetails.getEditorial());
            }
            if (bookDetails.getPublishedDate() != null) {
                book.setPublishedDate(bookDetails.getPublishedDate());
            }
            return bookRepository.save(book);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteById(String id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            bookRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean reserveBook(String bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent() && bookOptional.get().isAvailable()) {
            Book book = bookOptional.get();
            book.setAvailable(false);
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    @Override
    public boolean returnBook(String bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent() && !bookOptional.get().isAvailable()) {
            Book book = bookOptional.get();
            book.setAvailable(true);
            bookRepository.save(book);
            return true;
        }
        return false;
    }
}
