package com.bookingsystem.BookingSystem.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private String editorial;
    private String publishedDate;
    private boolean available;
    private String status;
}
