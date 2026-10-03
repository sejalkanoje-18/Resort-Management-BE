package com.ResortManagementBE.RRMS.security.audit;

import com.ResortManagementBE.RRMS.security.principal.CustomUserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AuditService {

    public void logAction(String action, String resourceType, Long resourceId) {
        String username = getCurrentUsername();
        log.info("[AUDIT] User='{}' Action='{}' ResourceType='{}' ResourceId='{}' At='{}'",
                username, action, resourceType, resourceId, LocalDateTime.now());
    }

    public void logAction(String action, String details) {
        String username = getCurrentUsername();
        log.info("[AUDIT] User='{}' Action='{}' Details='{}' At='{}'",
                username, action, details, LocalDateTime.now());
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserPrincipal principal) {
            return principal.getUsername();
        }
        return "anonymous";
    }
}
