package com.tugce.ecommerce.mapper;

import com.tugce.ecommerce.dto.UserRequestDTO;
import com.tugce.ecommerce.dto.UserResponseDTO;
import com.tugce.ecommerce.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static User toEntity(UserRequestDTO requestDTO){
        User user = new User();
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setEmail(requestDTO.getEmail());
        user.setPhoneNumber(requestDTO.getPhoneNumber());
        user.setAddress(requestDTO.getAddress());
        user.setPassword(requestDTO.getPassword());

        return user;
    }

    public UserResponseDTO toResponseDTO(User user){
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setFirstName(user.getFirstName());
        responseDTO.setLastName(user.getLastName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setPhoneNumber(user.getPhoneNumber());
        responseDTO.setAddress(user.getAddress());
        responseDTO.setRole(user.getRole());

        return responseDTO;
    }
}
