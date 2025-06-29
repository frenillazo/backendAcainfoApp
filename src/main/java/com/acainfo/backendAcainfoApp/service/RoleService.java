package com.acainfo.backendAcainfoApp.service;

import com.acainfo.backendAcainfoApp.domain.Role;
import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(Long id);

    Role create(Role role);

    Role update(Long id, Role role);

    void delete(Long id);
}
