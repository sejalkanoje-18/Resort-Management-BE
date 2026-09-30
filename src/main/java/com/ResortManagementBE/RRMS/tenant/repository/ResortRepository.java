package com.ResortManagementBE.RRMS.tenant.repository;

import com.ResortManagementBE.RRMS.tenant.entity.Resort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResortRepository extends JpaRepository<Resort, Long> {
}
