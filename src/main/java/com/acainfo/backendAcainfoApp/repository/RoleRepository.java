package com.acainfo.backendAcainfoApp.repository;

import com.acainfo.backendAcainfoApp.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
