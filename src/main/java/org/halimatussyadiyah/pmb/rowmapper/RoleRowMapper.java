package org.halimatussyadiyah.pmb.rowmapper;

import org.halimatussyadiyah.pmb.domain.Roles;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Roles> {
    @Override
    public Roles mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Roles.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .permission(resultSet.getString("permission"))
                .build();
    }
}
