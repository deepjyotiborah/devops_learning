package com.example.demoservice.service;

import com.example.demoservice.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public UserService() {
        // Initialize with some sample data
        users.add(new User(idCounter.getAndIncrement(), "John Doe", "john.doe@example.com"));
        users.add(new User(idCounter.getAndIncrement(), "Jane Smith", "jane.smith@example.com"));
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public Optional<User> getUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public User createUser(User user) {
        user.setId(idCounter.getAndIncrement());
        users.add(user);
        return user;
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                updatedUser.setId(id);
                users.set(i, updatedUser);
                return Optional.of(updatedUser);
            }
        }
        return Optional.empty();
    }

    public boolean deleteUser(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }
}

