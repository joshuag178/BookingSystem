package com.bookingsystem.BookingSystem.service.user;

import com.bookingsystem.BookingSystem.repository.user.User;

import java.util.List;

public interface UsersService {
    User save(User user);

    User findById(int id);

    List<User> all();

    void deleteById(int id);

    User update( int userId,User user);
}
