package com.bookingsystem.BookingSystem.controller;

import com.bookingsystem.BookingSystem.constants.ERole;
import com.bookingsystem.BookingSystem.repository.entity.Role;
import com.bookingsystem.BookingSystem.repository.entity.User;
import com.bookingsystem.BookingSystem.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserControllerTest.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "jbarreda", roles = {"USER"})
    void getAllUser_shouldReturnListOfUser() throws Exception{
        //Arrange
        Set<Role> roles = new HashSet<>(Arrays.asList(new Role("1", ERole.ROLE_USER), new Role("2", ERole.ROLE_ADMIN)));

        List<User> users = Arrays.asList(
                new User("1", "user1", "User 1", "Users1", "email1@example.com", "password1", true, roles),
                new User("2", "user2", "User 2", "Users2", "email2@example.com", "password2", true, roles)
        );

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
