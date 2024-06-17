package com.bookingsystem.BookingSystem.service;

import com.bookingsystem.BookingSystem.constants.ERole;
import com.bookingsystem.BookingSystem.repository.UserRepository;
import com.bookingsystem.BookingSystem.repository.entity.Role;
import com.bookingsystem.BookingSystem.repository.entity.User;
import com.bookingsystem.BookingSystem.service.impl.UserService;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllUsers_shouldReturnUserList() {
        // Arrange
        Set<Role> roles = new HashSet<>(List.of(
                new Role("1", ERole.ROLE_USER)
        )
        );

        User user1 = new User("1", "user1", "User 1", "User1", "user1@example.com", "password1", true, roles);
        User user2 = new User("2", "user2", "User 2", "User2", "user2@example.com", "password2", true, roles);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<User> users = userService.getAllUsers();

        // Assert
        assertEquals(2, users.size());
        // Verifica que el método findAll del repositorio fue llamado exactamente una vez
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void existsByUsername_shouldReturnTrue() {
        // Arrange
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // Act
        boolean exists = userService.existsByUsername("user1");

        // Assert
        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername(anyString());
    }

    @Test
    public void existsByEmail_shouldReturnTrue() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act
        boolean exists = userService.existsByEmail("user1@example.com");

        // Assert
        assertTrue(exists);
        verify(userRepository, times(1)).existsByEmail(anyString());
    }

    @Test
    public void findById_shouldReturnUser() {
        // Arrange
        Set<Role> rol = new HashSet<>(List.of(
                new Role("1", ERole.ROLE_USER)
        )
        );

        User user = new User("1", "user1", "User 1", "User1", "user1@example.com", "password1", true, rol);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        // Act
        Optional<User> foundUser = userService.findById("1");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("user1", foundUser.get().getUsername());
        verify(userRepository, times(1)).findById(anyString());
    }

    @Test
    public void save_shouldSaveUser() {
        // Arrange
        Set<Role> rol = new HashSet<>(List.of(
                new Role("1", ERole.ROLE_USER)
        )
        );

        User user = new User("1", "user1", "User 1", "User1", "user1@example.com", "password1", true, rol);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        userService.save(user);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void updateUser_shouldReturnUpdatedUser() {
        // Arrange
        Set<Role> roles = new HashSet<>(List.of(
                new Role("1", ERole.ROLE_USER)
        )
        );

        User existingUser = new User("1", "user1", "User 1", "User1", "user1@example.com", "password1", true, roles);
        User userDetails = new User("1", "user2", "User 2", "User2", "user2@example.com", "password2", true, roles);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(userDetails);

        // Act
        User updatedUser = userService.updateUser("1", userDetails);

        // Assert
        assertNotNull(updatedUser);
        assertEquals("user2", updatedUser.getUsername());
        assertEquals("user2@example.com", updatedUser.getEmail());
        verify(userRepository, times(1)).findById(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void deleteById_shouldReturnTrue() {
        // Arrange
        Set<Role> rol = new HashSet<>(List.of(
                new Role("1", ERole.ROLE_USER)
        )
        );

        User user = new User("1", "user1", "User 1", "User1", "user1@example.com", "password1", true, rol);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        // Act
        boolean deleted = userService.deleteById("1");

        // Assert
        assertTrue(deleted);
        verify(userRepository, times(1)).findById(anyString());
        verify(userRepository, times(1)).deleteById(anyString());
    }

    @Test
    public void deleteById_shouldReturnFalseWhenUserNotFound() {
        // Arrange
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        boolean deleted = userService.deleteById("1");

        // Assert
        assertFalse(deleted);
        verify(userRepository, times(1)).findById(anyString());
        verify(userRepository, times(0)).deleteById(anyString());
    }
}
