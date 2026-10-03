package com.ResortManagementBE.RRMS.auth.dto.response;

import com.ResortManagementBE.RRMS.auth.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;

    private Long userId;

    private String username;

    private Role role;

    private Long tenantId;
}
