package com.ResortManagementBE.RRMS.tenant.service;

import com.ResortManagementBE.RRMS.common.exception.ResourceNotFoundException;
import com.ResortManagementBE.RRMS.tenant.entity.Resort;
import com.ResortManagementBE.RRMS.tenant.repository.ResortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResortService {

    private final ResortRepository resortRepository;

    public Resort getResortById(Long id) {
        return resortRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resort", "id", id));
    }

    public List<Resort> getAllResorts() {
        return resortRepository.findAll();
    }

    public Resort updateResort(Long id, String name) {
        Resort resort = getResortById(id);
        resort.setName(name);
        return resortRepository.save(resort);
    }
}
