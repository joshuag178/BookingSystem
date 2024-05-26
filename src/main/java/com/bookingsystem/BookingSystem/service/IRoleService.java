package com.bookingsystem.BookingSystem.service;

import com.bookingsystem.BookingSystem.repository.entity.Role;

import java.util.List;

public interface IRoleService {
    void save(Role role);
    List<Role> getAllRoles();

}
