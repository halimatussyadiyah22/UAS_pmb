package org.halimatussyadiyah.pmb.service;

import org.halimatussyadiyah.pmb.domain.Roles;

import java.util.Collection;

public interface RoleService {
    Roles getRoleByUserId(Long id);

    Collection<Roles> getRoles();
}
