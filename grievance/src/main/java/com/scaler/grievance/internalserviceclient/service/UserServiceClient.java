package com.scaler.grievance.internalserviceclient.service;

import com.scaler.grievance.entities.User;
import com.scaler.grievance.repositories.UserRepository;
import com.scaler.shared.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceClient {
    private final UserRepository userRepository;

    UserServiceClient(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findByUsername(String username) throws NotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        return user.get();
    }
}
