package com.tugce.ecommerce.service;

import com.tugce.ecommerce.dto.UserResponseDTO;
import com.tugce.ecommerce.dto.UserRequestDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO saveUser(UserRequestDTO userRequestDTO);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);
    void deleteUser(Long id);
}
