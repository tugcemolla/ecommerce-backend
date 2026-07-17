package com.tugce.ecommerce.service.impl;

import com.tugce.ecommerce.dto.UserRequestDTO;
import com.tugce.ecommerce.dto.UserResponseDTO;
import com.tugce.ecommerce.entity.User;
import com.tugce.ecommerce.exception.ResourceNotFoundException;
import com.tugce.ecommerce.mapper.UserMapper;
import com.tugce.ecommerce.repository.UserRepository;
import com.tugce.ecommerce.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        user.setPassword(
                passwordEncoder.encode(userRequestDTO.getPassword())
        );
        user.setRole("CUSTOMER");
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->   new ResourceNotFoundException("Kullanıcı bulunamadı."));
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->   new ResourceNotFoundException("Kullanıcı bulunamadı."));
        existingUser.setFirstName(userRequestDTO.getFirstName());
        existingUser.setLastName(userRequestDTO.getLastName());
        existingUser.setEmail(userRequestDTO.getEmail());
        existingUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        existingUser.setPhoneNumber(userRequestDTO.getPhoneNumber());
        existingUser.setAddress(userRequestDTO.getAddress());

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toResponseDTO(updatedUser);

    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->   new ResourceNotFoundException("Kullanıcı bulunamadı."));

        userRepository.delete(user);
    }

}
