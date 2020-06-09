package com.alcosystem.alcosystem.repository.mappers;

import com.alcosystem.alcosystem.models.AlcoPerDepartment;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlcoPerDepartmentMapper implements RowMapper<AlcoPerDepartment> {

    @Override
    public AlcoPerDepartment mapRow(ResultSet resultSet, int i) throws SQLException {
        return new AlcoPerDepartment(
                resultSet.getString("department_name"),
                resultSet.getInt("alco_quantity")
        );
    }

}
