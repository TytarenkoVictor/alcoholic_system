package com.alcosystem.alcosystem.repository.mappers;

import com.alcosystem.alcosystem.models.BedColorQuantity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BedColorMapper implements RowMapper<BedColorQuantity> {

    @Override
    public BedColorQuantity mapRow(ResultSet resultSet, int i) throws SQLException {
        return new BedColorQuantity(
                resultSet.getString("bed_color"),
                resultSet.getInt("color_quantity")
        );
    }

}
