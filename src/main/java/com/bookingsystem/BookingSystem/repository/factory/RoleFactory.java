package com.bookingsystem.BookingSystem.repository.factory;

import com.bookingsystem.BookingSystem.constants.ERole;
import com.bookingsystem.BookingSystem.handler.exception.RoleNotFoundException;
import com.bookingsystem.BookingSystem.repository.RoleRepository;
import com.bookingsystem.BookingSystem.repository.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleFactory {
    @Autowired
    RoleRepository roleRepository;

    public Role getInstance(String role) throws RoleNotFoundException {
        switch (role) {
            case "admin" -> {
                return roleRepository.findByName(ERole.ROLE_ADMIN);
            }
            case "user" -> {
                return roleRepository.findByName(ERole.ROLE_USER);
            }
            case "super_admin" -> {
                return roleRepository.findByName(ERole.ROLE_SUPER_ADMIN);
            }
            default -> throw  new RoleNotFoundException("No role found for " +  role);
        }
    }
}
