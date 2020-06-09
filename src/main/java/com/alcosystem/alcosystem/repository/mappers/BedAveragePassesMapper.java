package com.alcosystem.alcosystem.repository.mappers;

import com.alcosystem.alcosystem.models.BedAveragePasses;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BedAveragePassesMapper implements RowMapper<BedAveragePasses> {

    @Override
    public BedAveragePasses mapRow(ResultSet resultSet, int i) throws SQLException {
        return new BedAveragePasses(
                resultSet.getLong("id"),
                resultSet.getDouble("average_passes")
        );
    }
}
