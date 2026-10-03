package com.ResortManagementBE.RRMS.security.tenant;

import com.ResortManagementBE.RRMS.security.principal.CustomUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TenantResolver {

    public Long resolveTenantId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserPrincipal principal) {
            return principal.getTenantId();
        }
        return null;
    }

    public Long getCurrentTenantId() {
        return TenantContext.getTenantId();
    }
}
