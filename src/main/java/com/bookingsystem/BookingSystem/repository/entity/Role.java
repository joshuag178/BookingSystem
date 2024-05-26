package com.bookingsystem.BookingSystem.repository.entity;

import com.bookingsystem.BookingSystem.constants.ERole;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
@Data
public class Role {
    @Id
    private String id;

    private ERole name;
}
