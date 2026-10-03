package com.ResortManagementBE.RRMS.superadmin.dto.response;

import com.ResortManagementBE.RRMS.auth.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerResponse {

    private Long id;
    private String username;
    private Role role;
    private Long resortId;
    private String resortName;
}
