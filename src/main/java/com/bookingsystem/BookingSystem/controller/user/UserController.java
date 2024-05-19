package com.bookingsystem.BookingSystem.controller.user;

import com.bookingsystem.BookingSystem.repository.user.User;
import com.bookingsystem.BookingSystem.service.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")

public class UserController {
    private final UsersService usersService;

    public UserController(@Autowired UsersService usersService) {
        this.usersService = usersService;
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> user = usersService.all();
        return ResponseEntity.ok(user);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable("id") int id) {
        User user  = usersService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createProduct(@RequestBody User newUser) {
        URI createdUserUri = URI.create("");
        User user = usersService.save(newUser);
        return ResponseEntity.created(createdUserUri).body(user);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        return usersService.update(id, user);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable int id) {
        usersService.deleteById(id);
    }
}
