package com.bookingsystem.BookingSystem.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {
    @Id
    private String id;
    private String username;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private boolean enabled;

    @DBRef
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
}
