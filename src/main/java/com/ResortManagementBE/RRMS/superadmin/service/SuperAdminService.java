package com.ResortManagementBE.RRMS.superadmin.service;

import com.ResortManagementBE.RRMS.auth.entity.Role;
import com.ResortManagementBE.RRMS.auth.entity.User;
import com.ResortManagementBE.RRMS.auth.repository.UserRepository;
import com.ResortManagementBE.RRMS.common.exception.ResourceNotFoundException;
import com.ResortManagementBE.RRMS.superadmin.dto.request.CreateOwnerRequest;
import com.ResortManagementBE.RRMS.superadmin.dto.response.OwnerResponse;
import com.ResortManagementBE.RRMS.tenant.entity.Resort;
import com.ResortManagementBE.RRMS.tenant.repository.ResortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SuperAdminService {

    private final UserRepository userRepository;
    private final ResortRepository resortRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Create a new resort owner and their resort.
     */
    @Transactional
    public OwnerResponse createOwner(CreateOwnerRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username '" + request.getUsername() + "' is already taken");
        }

        // Create the resort
        Resort resort = new Resort();
        resort.setName(request.getResortName());
        resort = resortRepository.save(resort);

        // Create the owner user
        User owner = new User();
        owner.setUsername(request.getUsername());
        owner.setPassword(passwordEncoder.encode(request.getPassword()));
        owner.setRole(Role.OWNER);
        owner.setResort(resort);
        owner = userRepository.save(owner);

        return mapToOwnerResponse(owner);
    }

    /**
     * Get all resort owners.
     */
    public List<OwnerResponse> getAllOwners() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == Role.OWNER)
                .map(this::mapToOwnerResponse)
                .toList();
    }

    /**
     * Get owner by ID.
     */
    public OwnerResponse getOwnerById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (user.getRole() != Role.OWNER) {
            throw new ResourceNotFoundException("Owner", "id", id);
        }

        return mapToOwnerResponse(user);
    }

    /**
     * Delete an owner and their resort.
     */
    @Transactional
    public void deleteOwner(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (user.getRole() != Role.OWNER) {
            throw new ResourceNotFoundException("Owner", "id", id);
        }

        Resort resort = user.getResort();
        userRepository.delete(user);

        if (resort != null) {
            resortRepository.delete(resort);
        }
    }

    private OwnerResponse mapToOwnerResponse(User user) {
        OwnerResponse response = new OwnerResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        if (user.getResort() != null) {
            response.setResortId(user.getResort().getId());
            response.setResortName(user.getResort().getName());
        }
        return response;
    }
}
