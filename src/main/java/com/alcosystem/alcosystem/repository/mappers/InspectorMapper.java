package com.alcosystem.alcosystem.repository.mappers;

import com.alcosystem.alcosystem.models.Inspector;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class InspectorMapper implements RowMapper<Inspector> {

    @Override
    public Inspector mapRow(ResultSet resultSet, int i) throws SQLException {

        Integer takenQuantity;
        try {
            takenQuantity = resultSet.getInt("taken_quantity");
        } catch (Exception ex) {
            takenQuantity = null;
        }

        Integer alcoholicTaken;
        try {
            alcoholicTaken = resultSet.getInt("alcoholic_taken");
        } catch (Exception ex) {
            alcoholicTaken = null;
        }

        return new Inspector(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("hire_date").toLocalDate(),
                resultSet.getLong("department_id"), takenQuantity, alcoholicTaken);
    }

}
