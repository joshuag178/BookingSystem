package com.bookingsystem.BookingSystem.service.user;

import com.bookingsystem.BookingSystem.repository.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsersServiceMap implements UsersService {
    private static final Map<Integer, User> userRepository = new HashMap<>();

    @Override
    public User save(User user) {
        if (userRepository.containsKey(user.getId())) {
            throw new RuntimeException("User already exists");
        }
        userRepository.put(user.getId(), user);
        return user;
    }

    @Override
    public User findById(int id) {
        return userRepository.get(id);
    }

    @Override
    public List<User> all() {
        return new ArrayList<>(userRepository.values());
    }

    @Override
    public void deleteById(int id) {
        if (userRepository.containsKey(id)) {
            userRepository.remove(id);
        }
    }

    @Override
    public User update(int userId,User user ) {
        if (!userRepository.containsKey(userId)) {
            throw new RuntimeException("User does not exist");
        }
        userRepository.put(user.getId(), user);
        return user;
    }
}
