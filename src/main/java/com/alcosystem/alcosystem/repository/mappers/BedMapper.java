package com.alcosystem.alcosystem.repository.mappers;

import com.alcosystem.alcosystem.models.Bed;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BedMapper implements RowMapper<Bed> {

    @Override
    public Bed mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Bed(
                resultSet.getLong("id"),
                resultSet.getString("color"),
                resultSet.getString("size")
        );
    }

}
