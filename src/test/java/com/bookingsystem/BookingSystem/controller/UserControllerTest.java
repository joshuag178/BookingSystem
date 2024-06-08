package com.bookingsystem.BookingSystem.controller;

import com.bookingsystem.BookingSystem.config.TestSecurityConfig;
import com.bookingsystem.BookingSystem.constants.ERole;
import com.bookingsystem.BookingSystem.repository.entity.Role;
import com.bookingsystem.BookingSystem.repository.entity.User;
import com.bookingsystem.BookingSystem.service.impl.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    /*@Autowired
    private PasswordEncoder passwordEncoder;*/

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllUser_shouldReturnListOfUser() throws Exception{
        //Arrange
        Set<Role> roles = new HashSet<>(Arrays.asList(
                new Role("1", ERole.ROLE_USER),
                new Role("2", ERole.ROLE_ADMIN)
            )
        );

        List<User> users = Arrays.asList(
                new User("1", "user1", "User 1", "Users1", "email1@example.com", "password1", true, roles),
                new User("2", "user2", "User 2", "Users2", "email2@example.com", "password2", true, roles)
        );

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /*@Test
    void getUserById_shouldReturnUserById() throws Exception {
        // Arrange
        Set<Role> roles = new HashSet<>(List.of(
                new Role("1", ERole.ROLE_USER)
        )
        );

        User user = new User("1", "user1", "User 1", "Users1",
                "email1@example.com", "password1", true, roles);

        when(userService.findById("1")).thenReturn(Optional.of(user));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()));

        verify(userService, times(1)).findById("1");
    }*/

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        // Arrange
        Set<Role> roles = new HashSet<>(List.of(
                new Role("1", ERole.ROLE_USER)
        )
        );

        String userId = "1";
        User user = new User(userId, "user1", "User 1", "Users1", "email1@example.com", "password1", true, roles);
        when(userService.findById(userId)).thenReturn(Optional.of(user));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.username").value(user.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.email").value(user.getEmail()));
    }

    @Test
    void getUserById_shouldReturnNotFound() throws Exception {
        // Arrange
        String userId = "2";
        when(userService.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("User not found with ID: " + userId));
    }

    @Test
    void createUser_shouldReturnCreateUser() throws Exception{
        //Arrange
        Set<Role> roles = new HashSet<>(List.of(
                new Role("1", ERole.ROLE_USER)
        )
        );

        User user = new User("1", "user1", "User 1", "Users1",
                "email1@example.com", "password1", true, roles);

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()));

        verify(userService, times(1)).save(user);
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() throws Exception {
        // Arrange
        Set<Role> roles = new HashSet<>(List.of(
                new Role("1", ERole.ROLE_USER)
        )
        );

        String userId = "1";
        User updatedUserDetails = new User(userId, "user1", "User 1", "Users1", "email1@example.com", "password1", true, roles);
        User updatedUser = new User(userId, "user1", "User 2", "Users2", "email2@example.com", "password2", true, roles);

        when(userService.updateUser(userId, updatedUserDetails)).thenReturn(updatedUser);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUserDetails)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(updatedUser.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(updatedUser.getEmail()));

        verify(userService, times(1)).updateUser(userId, updatedUserDetails);
    }

    @Test
    void updateUser_shouldReturnNotFound() throws Exception {
        // Arrange
        Set<Role> roles = new HashSet<>(List.of(
                new Role("1", ERole.ROLE_USER)
        )
        );

        String userId = "2";
        User updatedUserDetails = new User(userId, "user2", "User 2", "Users2", "email2@example.com", "password2", true, roles);

        when(userService.updateUser(userId, updatedUserDetails)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUserDetails)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteUser_shouldReturnSuccessMessage() throws Exception {
        // Arrange
        String userId = "1";

        when(userService.deleteById(userId)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("User deleted successfully."));

        verify(userService, times(1)).deleteById(userId);
    }

    @Test
    void deleteUser_shouldReturnNotFoundMessage() throws Exception {
        // Arrange
        String userId = "2";

        when(userService.deleteById(userId)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("User not found."));

        verify(userService, times(1)).deleteById(userId);
    }
}
