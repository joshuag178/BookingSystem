package com.bookingsystem.BookingSystem.repository.entity;

import com.bookingsystem.BookingSystem.constants.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Document(collection = "roles")
@Data
public class Role {
    @Id
    private String id;

    private ERole name;

    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }

}
