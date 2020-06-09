package com.alcosystem.alcosystem.repository.mappers;

import com.alcosystem.alcosystem.models.Action;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActionMapper implements RowMapper<Action> {

    @Override
    public Action mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Action(
                resultSet.getDate("action_date").toLocalDate(),
                resultSet.getString("action")
        );
    }

}
