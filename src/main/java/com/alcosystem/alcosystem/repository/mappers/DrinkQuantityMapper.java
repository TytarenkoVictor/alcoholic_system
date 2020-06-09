package com.alcosystem.alcosystem.repository.mappers;

import com.alcosystem.alcosystem.models.DrinkQuantity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class DrinkQuantityMapper implements RowMapper<DrinkQuantity> {

    @Override
    public DrinkQuantity mapRow(ResultSet resultSet, int i) throws SQLException {
        return new DrinkQuantity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getLong("quantity")
        );
    }

}
