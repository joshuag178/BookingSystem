package com.bookingsystem.BookingSystem.service;

import com.bookingsystem.BookingSystem.dto.ApiResponseDto;
import com.bookingsystem.BookingSystem.dto.SignInRequestDto;
import com.bookingsystem.BookingSystem.dto.SignUpRequestDto;
import com.bookingsystem.BookingSystem.handler.exception.RoleNotFoundException;
import com.bookingsystem.BookingSystem.handler.exception.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseEntity<ApiResponseDto<?>> signUpUser(SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException, RoleNotFoundException;
    ResponseEntity<ApiResponseDto<?>> signInUser(SignInRequestDto signInRequestDto);
}
