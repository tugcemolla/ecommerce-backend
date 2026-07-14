package com.tugce.ecommerce.controller;

import com.tugce.ecommerce.dto.UserRequestDTO;
import com.tugce.ecommerce.dto.ProductResponseDTO;
import com.tugce.ecommerce.dto.UserResponseDTO;
import com.tugce.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDTO saveUser(
            @RequestBody @Valid UserRequestDTO userRequestDTO){
        return userService.saveUser(userRequestDTO);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UserRequestDTO userRequestDTO){
        return userService.updateUser(id, userRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

}
