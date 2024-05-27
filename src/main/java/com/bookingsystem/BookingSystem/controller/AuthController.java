package com.bookingsystem.BookingSystem.controller;

import com.bookingsystem.BookingSystem.dto.ApiResponseDto;
import com.bookingsystem.BookingSystem.dto.SignInRequestDto;
import com.bookingsystem.BookingSystem.dto.SignUpRequestDto;
import com.bookingsystem.BookingSystem.handler.exception.RoleNotFoundException;
import com.bookingsystem.BookingSystem.handler.exception.UserAlreadyExistsException;
import com.bookingsystem.BookingSystem.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<?>> registerUser(@RequestBody @Valid SignUpRequestDto signUpRequestDto)
            throws UserAlreadyExistsException, RoleNotFoundException {
        return authService.signUpUser(signUpRequestDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponseDto<?>> signInUser(@RequestBody SignInRequestDto signInRequestDto){
        return authService.signInUser(signInRequestDto);
    }
}
