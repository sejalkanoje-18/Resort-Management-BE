package com.ResortManagementBE.RRMS.security.authorization;

import com.ResortManagementBE.RRMS.auth.entity.Role;
import com.ResortManagementBE.RRMS.security.principal.CustomUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    public boolean hasRole(Role role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserPrincipal principal) {
            return principal.getRole() == role;
        }
        return false;
    }

    public boolean isSuperAdmin() {
        return hasRole(Role.SUPER_ADMIN);
    }

    public boolean isOwner() {
        return hasRole(Role.OWNER);
    }

    public boolean isManagement() {
        return hasRole(Role.MANAGEMENT);
    }

    public boolean isStaff() {
        return hasRole(Role.STAFF);
    }

    public CustomUserPrincipal getCurrentPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserPrincipal principal) {
            return principal;
        }
        return null;
    }
}
