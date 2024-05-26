package com.bookingsystem.BookingSystem.controller;

import com.bookingsystem.BookingSystem.repository.entity.Role;
import com.bookingsystem.BookingSystem.service.IRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")

public class RoleController {
    private final IRoleService roleService;
    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping
    public ResponseEntity<Role> createUser(@RequestBody Role role) {
        roleService.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }
}
