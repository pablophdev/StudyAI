package com.pabloph.auth_service.service;

import com.pabloph.auth_service.dto.RegisterRequest;

public interface AuthService {
    
    void register(RegisterRequest request);
}
