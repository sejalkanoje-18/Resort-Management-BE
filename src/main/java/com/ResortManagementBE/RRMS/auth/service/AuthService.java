package com.ResortManagementBE.RRMS.auth.service;

import com.ResortManagementBE.RRMS.auth.dto.request.LoginRequest;
import com.ResortManagementBE.RRMS.auth.dto.response.AuthResponse;
import com.ResortManagementBE.RRMS.security.jwt.JwtService;
import com.ResortManagementBE.RRMS.security.principal.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),
                                                        request.getPassword()
                )
        );

        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();

        String token = jwtService.generateToken(principal);

        return new AuthResponse(
                token,
                principal.getUserId(),
                principal.getUsername(),
                principal.getRole(),
                principal.getTenantId()
        );
    }

}
