package com.bookingsystem.BookingSystem.service.impl;

import com.bookingsystem.BookingSystem.repository.RoleRepository;
import com.bookingsystem.BookingSystem.repository.entity.Role;
import com.bookingsystem.BookingSystem.service.IRoleService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
