package org.halimatussyadiyah.pmb.rowmapper;



import org.halimatussyadiyah.pmb.domain.Stats;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsRowMapper implements RowMapper<Stats> {
    @Override
    public Stats mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Stats.builder()
                .totalBiodata(resultSet.getInt("total_biodata"))
                .totalCard(resultSet.getInt("total_card"))
                .build();
    }
}
