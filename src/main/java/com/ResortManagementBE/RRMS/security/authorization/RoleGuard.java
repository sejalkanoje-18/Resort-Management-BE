package com.ResortManagementBE.RRMS.security.authorization;

import com.ResortManagementBE.RRMS.auth.entity.Role;
import com.ResortManagementBE.RRMS.security.principal.CustomUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component("roleGuard")
public class RoleGuard {

    /**
     * Use with @PreAuthorize("@roleGuard.hasAnyRole('SUPER_ADMIN', 'OWNER')")
     */
    public boolean hasAnyRole(String... roles) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserPrincipal principal)) {
            return false;
        }
        return Arrays.stream(roles)
                .anyMatch(role -> principal.getRole().name().equals(role));
    }

    /**
     * Use with @PreAuthorize("@roleGuard.isTenantOwner(#tenantId)")
     */
    public boolean isTenantOwner(Long tenantId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserPrincipal principal)) {
            return false;
        }
        return principal.getRole() == Role.OWNER
                && tenantId != null
                && tenantId.equals(principal.getTenantId());
    }

    /**
     * Checks if the current user belongs to the given tenant.
     */
    public boolean belongsToTenant(Long tenantId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserPrincipal principal)) {
            return false;
        }
        if (principal.getRole() == Role.SUPER_ADMIN) {
            return true; // Super admin has access to all tenants
        }
        return tenantId != null && tenantId.equals(principal.getTenantId());
    }
}
