package com.tugce.ecommerce.service;

import com.tugce.ecommerce.dto.AuthResponseDTO;
import com.tugce.ecommerce.dto.LoginRequestDTO;


public interface AuthService{
    AuthResponseDTO login(LoginRequestDTO loginRequestDTO);


}
