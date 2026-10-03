package com.ResortManagementBE.RRMS.superadmin.controller;

import com.ResortManagementBE.RRMS.superadmin.dto.request.CreateOwnerRequest;
import com.ResortManagementBE.RRMS.superadmin.dto.response.OwnerResponse;
import com.ResortManagementBE.RRMS.superadmin.service.SuperAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/super-admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    /**
     * POST /api/super-admin/owners
     * Create a new resort owner and their resort.
     */
    @PostMapping("/owners")
    public ResponseEntity<OwnerResponse> createOwner(@Valid @RequestBody CreateOwnerRequest request) {
        OwnerResponse response = superAdminService.createOwner(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/super-admin/owners
     * Get all resort owners.
     */
    @GetMapping("/owners")
    public ResponseEntity<List<OwnerResponse>> getAllOwners() {
        return ResponseEntity.ok(superAdminService.getAllOwners());
    }

    /**
     * GET /api/super-admin/owners/{id}
     * Get a specific owner by ID.
     */
    @GetMapping("/owners/{id}")
    public ResponseEntity<OwnerResponse> getOwnerById(@PathVariable Long id) {
        return ResponseEntity.ok(superAdminService.getOwnerById(id));
    }

    /**
     * DELETE /api/super-admin/owners/{id}
     * Delete an owner and their resort.
     */
    @DeleteMapping("/owners/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        superAdminService.deleteOwner(id);
        return ResponseEntity.noContent().build();
    }
}
