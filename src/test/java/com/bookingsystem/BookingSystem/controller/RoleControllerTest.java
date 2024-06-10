package com.bookingsystem.BookingSystem.controller;

import com.bookingsystem.BookingSystem.config.TestSecurityConfig;
import com.bookingsystem.BookingSystem.constants.ERole;
import com.bookingsystem.BookingSystem.repository.entity.Role;
import com.bookingsystem.BookingSystem.service.impl.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoleController.class)
@Import(TestSecurityConfig.class)
public class RoleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllRoles_shouldReturnListOfRoles() throws Exception {
        List<Role> roles = Arrays.asList(
                new Role("1", ERole.ROLE_USER),
                new Role("2", ERole.ROLE_ADMIN)
        );
        when(roleService.getAllRoles()).thenReturn(roles);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/roles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("ROLE_USER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("ROLE_ADMIN"));
    }

    @Test
    void createUser_shouldCreateAndReturnRole() throws Exception {
        Role role = new Role("1", ERole.ROLE_SUPER_ADMIN);
        doNothing().when(roleService).save(any(Role.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(role)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("ROLE_SUPER_ADMIN"));

        verify(roleService, times(1)).save(any(Role.class));
    }
}
