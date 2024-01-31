package org.halimatussyadiyah.pmb.repository.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.halimatussyadiyah.pmb.domain.Roles;
import org.halimatussyadiyah.pmb.exception.ApiException;
import org.halimatussyadiyah.pmb.repository.RoleRepository;
import org.halimatussyadiyah.pmb.rowmapper.RoleRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

import static java.util.Map.of;
import static java.util.Objects.requireNonNull;
import static org.halimatussyadiyah.pmb.enumeration.RoleType.ROLE_MAHASISWA;
import static org.halimatussyadiyah.pmb.query.RoleQuery.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImpl implements RoleRepository<Roles> {
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Roles create(Roles data) {
        return null;
    }

    @Override
    public Collection<Roles> list() {
        log.info("Fetching all roles");
        try {
            return jdbc.query(SELECT_ROLES_QUERY, new RoleRowMapper());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }
    @Override
    public Roles get(Long id) {
        return null;
    }

    @Override
    public Roles update(Roles data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        log.info("Adding role {} to user id: {}", roleName, userId);
        try {
            Roles role = jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, of("name", roleName), new RoleRowMapper());
            jdbc.update(INSERT_ROLE_TO_USER_QUERY, of("userId", userId, "roleId", requireNonNull(role).getId()));
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No role found by name: " + ROLE_MAHASISWA.name());

        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Roles getRoleByUserId(Long userId) {
        log.info("Fetching role for user id: {}", userId);
        try {
            return jdbc.queryForObject(SELECT_ROLE_BY_ID_QUERY, of("id", userId), new RoleRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No role found by name: " + ROLE_MAHASISWA.name());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Roles getRoleByUserEmail(String email) {
        return null;
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {
        log.info("Updating role for user id: {}", userId);
        try {
            Roles roles = jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, of("name", roleName), new RoleRowMapper());
            jdbc.update(UPDATE_USER_ROLE_QUERY, of("roleId", requireNonNull(roles).getId(), "userId", userId));
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No role found by name: " + roleName);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }
}

