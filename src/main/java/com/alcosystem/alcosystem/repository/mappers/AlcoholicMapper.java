package com.alcosystem.alcosystem.repository.mappers;

import com.alcosystem.alcosystem.models.Alcoholic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlcoholicMapper implements RowMapper<Alcoholic> {

    @Override
    public Alcoholic mapRow(ResultSet resultSet, int i) throws SQLException {
        Integer takenQuantity;
        try {
            takenQuantity = resultSet.getInt("taken_quantity");
        } catch (Exception ex) {
            takenQuantity = null;
        }

        return new Alcoholic(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getDate("birth_date").toLocalDate(),
                takenQuantity
        );
    }

}
