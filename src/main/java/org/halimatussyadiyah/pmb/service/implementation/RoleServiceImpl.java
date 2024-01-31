package org.halimatussyadiyah.pmb.service.implementation;

import lombok.RequiredArgsConstructor;
import org.halimatussyadiyah.pmb.domain.Roles;
import org.halimatussyadiyah.pmb.repository.RoleRepository;
import org.halimatussyadiyah.pmb.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository<Roles> roleRoleRepository;

    @Override
    public Roles getRoleByUserId(Long id) {
        return roleRoleRepository.getRoleByUserId(id);
    }

    @Override
    public Collection<Roles> getRoles() {
        return roleRoleRepository.list();
    }
}
