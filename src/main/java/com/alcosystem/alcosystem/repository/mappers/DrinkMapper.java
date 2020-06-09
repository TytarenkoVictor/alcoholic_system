package com.alcosystem.alcosystem.repository.mappers;

import com.alcosystem.alcosystem.models.Drink;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DrinkMapper implements RowMapper<Drink> {
    @Override
    public Drink mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Drink(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("degree")
        );
    }
}
