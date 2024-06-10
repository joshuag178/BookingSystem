package com.bookingsystem.BookingSystem.controller;

import com.bookingsystem.BookingSystem.config.TestSecurityConfig;
import com.bookingsystem.BookingSystem.handler.exception.BookNotAvailableException;
import com.bookingsystem.BookingSystem.handler.exception.ResourceNotFoundException;
import com.bookingsystem.BookingSystem.repository.entity.Book;
import com.bookingsystem.BookingSystem.repository.entity.Loan;
import com.bookingsystem.BookingSystem.repository.entity.User;
import com.bookingsystem.BookingSystem.service.impl.LoanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoanController.class)
@Import(TestSecurityConfig.class)

public class LoanControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void getAllLoans_shouldReturnListOfLoans() throws Exception {
        List<Loan> loans = Arrays.asList(
                new Loan("1", new User(), new Book(), LocalDate.now(), null, "Richard", "Smith", "123456789", "Chimaltenango", "smith@example.com", "Loan"),
                new Loan("2", new User(), new Book(), LocalDate.now(), null, "Jose", "Yun", "987654321", "Guatemala", "josueyun@example.com", "Booking")
        );
        when(loanService.getAllLoans()).thenReturn(loans);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/loans"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }

    @Test
    void getLoanById_shouldReturnLoan() throws Exception {
        Loan loan = new Loan("1", new User(), new Book(), LocalDate.now(), null, "Richard", "Smith", "123456789", "Chimaltenango", "smith@example.com", "Loan");
        when(loanService.findById("1")).thenReturn(Optional.of(loan));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/loans/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.borrowerName").value("Richard"));
    }

    @Test
    void getLoanById_shouldReturnNotFound() throws Exception {
        when(loanService.findById("1")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/loans/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getLoansByUserId_shouldReturnListOfLoans() throws Exception {
        List<Loan> loans = Arrays.asList(
                new Loan("1", new User(), new Book(), LocalDate.now(), null, "Richard", "Smith", "123456789", "Chimaltenango", "smith@example.com", "Loan"),
                new Loan("2", new User(), new Book(), LocalDate.now(), null, "Jose", "Yun", "987654321", "Guatemala", "josueyun@example.com", "Booking")
        );
        when(loanService.findByUserId("1")).thenReturn(loans);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/loans/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }

    @Test
    void getLoansByBookId_shouldReturnListOfLoans() throws Exception {
        List<Loan> loans = List.of(
                new Loan("1", new User(), new Book(), LocalDate.now(), null, "Richard", "Smith", "123456789", "Chimaltenango", "smith@example.com", "Loan")
        );
        when(loanService.findByBookId("1")).thenReturn(loans);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/loans/book/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1));
    }

    @Test
    void createLoan_shouldReturnCreatedLoan() throws Exception {
        User user = new User();
        user.setId("1");
        Book book = new Book();
        book.setId("1");

        Loan loan = new Loan("1", new User(), new Book(), LocalDate.now(), null, "Richard", "Smith", "123456789", "Chimaltenango", "smith@example.com", "Loan");
        when(loanService.createLoan(any(Loan.class))).thenReturn(loan);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loan)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.borrowerName").value("Richard"));

        verify(loanService, times(1)).createLoan(loan);
    }

    @Test
    void createLoan_shouldReturnBadRequest_whenBookNotAvailable() throws Exception {
        User user = new User();
        user.setId("1");
        Book book = new Book();
        book.setId("1");

        Loan loan = new Loan("1", new User(), new Book(), LocalDate.now(), null, "Richard", "Smith", "123456789", "Chimaltenango", "smith@example.com", "Loan");
        when(loanService.createLoan(any(Loan.class))).thenThrow(new BookNotAvailableException("Book is not available for loan"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loan)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Book is not available for loan"));
    }

    @Test
    void createLoan_shouldReturnBadRequest_whenResourceNotFound() throws Exception {
        User user = new User();
        user.setId("1");
        Book book = new Book();
        book.setId("1");

        Loan loan = new Loan("1", user, book, LocalDate.now(), null, "John", "Doe", "123456789", "123 Main St", "john.doe@example.com", "Loan");
        when(loanService.createLoan(any(Loan.class))).thenThrow(new ResourceNotFoundException("User or Book not found"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loan)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("User or Book not found"));
    }

    @Test
    void returnLoan_shouldReturnSuccess() throws Exception {
        when(loanService.returnLoan("1")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/loans/return/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Loan returned successfully."));
    }

    @Test
    void returnLoan_shouldReturnNotFound() throws Exception {
        when(loanService.returnLoan("1")).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/loans/return/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Loan not found or already returned."));
    }
}
