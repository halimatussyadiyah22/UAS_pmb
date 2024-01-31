package org.halimatussyadiyah.pmb.repository;

import org.halimatussyadiyah.pmb.domain.Roles;

import java.util.Collection;

public interface RoleRepository <T extends Roles> {
    T create(T data);

    Collection<T> list();

    T get(Long id);

    T update(T data);

    Boolean delete(Long id);

    /* More Complex Operations */
    void addRoleToUser(Long userId, String roleName);

    Roles getRoleByUserId(Long userId);

    Roles getRoleByUserEmail(String email);

    void updateUserRole(Long userId, String roleName);
}

