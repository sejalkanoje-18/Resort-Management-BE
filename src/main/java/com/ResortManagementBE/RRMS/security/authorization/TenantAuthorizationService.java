package com.ResortManagementBE.RRMS.security.authorization;

import com.ResortManagementBE.RRMS.auth.entity.Role;
import com.ResortManagementBE.RRMS.security.principal.CustomUserPrincipal;
import com.ResortManagementBE.RRMS.security.tenant.TenantContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TenantAuthorizationService {

    /**
     * Ensures the currently authenticated user belongs to the given tenant,
     * or is a SUPER_ADMIN.
     *
     * @throws AccessDeniedException if the user does not belong to the tenant
     */
    public void assertTenantAccess(Long tenantId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserPrincipal principal)) {
            throw new AccessDeniedException("Not authenticated");
        }

        if (principal.getRole() == Role.SUPER_ADMIN) {
            return; // Super admin has access to all tenants
        }

        if (!tenantId.equals(principal.getTenantId())) {
            throw new AccessDeniedException("You do not have access to this tenant's resources");
        }
    }

    /**
     * Returns the tenant ID from the current TenantContext.
     */
    public Long getCurrentTenantId() {
        return TenantContext.getTenantId();
    }
}
