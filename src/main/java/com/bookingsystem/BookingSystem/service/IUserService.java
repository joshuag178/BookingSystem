package com.bookingsystem.BookingSystem.service;

import com.bookingsystem.BookingSystem.repository.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IUserService {
    List<User> getAllUsers();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findById(String id);
    void save(User user);
    User updateUser(String id, User userDetails);
    boolean deleteById(String id);
}
