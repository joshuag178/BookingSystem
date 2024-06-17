package com.bookingsystem.BookingSystem.service;

import com.bookingsystem.BookingSystem.constants.ERole;
import com.bookingsystem.BookingSystem.repository.RoleRepository;
import com.bookingsystem.BookingSystem.repository.entity.Role;
import com.bookingsystem.BookingSystem.service.impl.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RoleServiceTest {
    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void save_shouldSaveRole() {
        // Arrange
        Role role = new Role();
        role.setId("role1");
        role.setName(ERole.ROLE_ADMIN);

        // Act
        roleService.save(role);

        // Assert
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    public void getAllRoles_shouldReturnRoleList() {
        // Arrange
        Role role1 = new Role();
        Role role2 = new Role();
        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

        // Act
        List<Role> roles = roleService.getAllRoles();

        // Assert
        assertEquals(2, roles.size());
        verify(roleRepository, times(1)).findAll();
    }

}
