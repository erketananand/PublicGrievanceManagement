package com.scaler.user.services;

import com.scaler.user.dto.UserDTO;
import com.scaler.user.exceptionsHandler.NotFoundException;
import com.scaler.user.models.User;

import java.util.List;

public interface UserService {
    User registerUser(UserDTO userDTO) throws NotFoundException;
    List<User> getAllUsers();
    User getUserById(Long id) throws NotFoundException;

    User updateUser(Long id, UserDTO userDTO) throws NotFoundException;

    User deleteUser(Long id) throws NotFoundException;;
}
