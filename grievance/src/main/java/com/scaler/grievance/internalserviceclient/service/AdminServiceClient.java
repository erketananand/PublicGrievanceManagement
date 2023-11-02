package com.scaler.grievance.internalserviceclient.service;

import com.scaler.grievance.entities.User;
import com.scaler.grievance.repositories.UserRepository;
import com.scaler.shared.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceClient {
    private final UserRepository userRepository;

    public AdminServiceClient(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getSuperAdmin() throws NotFoundException {
        String superAdminUser = "naman@scaler.com";
        Optional<User> user = userRepository.findByUsername(superAdminUser);
        if(user.isEmpty()) {
            throw new NotFoundException("No Super admin not found");
        }
        return user.get();
    }

}
