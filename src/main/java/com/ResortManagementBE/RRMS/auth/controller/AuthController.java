package com.ResortManagementBE.RRMS.auth.controller;

import com.ResortManagementBE.RRMS.auth.dto.request.LoginRequest;
import com.ResortManagementBE.RRMS.auth.dto.response.AuthResponse;
import com.ResortManagementBE.RRMS.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

}
