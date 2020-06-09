package com.alcosystem.alcosystem.repository;

import com.alcosystem.alcosystem.models.Action;
import com.alcosystem.alcosystem.models.AlcoPerDepartment;
import com.alcosystem.alcosystem.models.Alcoholic;
import com.alcosystem.alcosystem.models.Bed;
import com.alcosystem.alcosystem.models.BedAveragePasses;
import com.alcosystem.alcosystem.models.BedColorQuantity;
import com.alcosystem.alcosystem.models.Drink;
import com.alcosystem.alcosystem.models.DrinkQuantity;
import com.alcosystem.alcosystem.models.Inspector;
import com.alcosystem.alcosystem.repository.mappers.ActionMapper;
import com.alcosystem.alcosystem.repository.mappers.AlcoPerDepartmentMapper;
import com.alcosystem.alcosystem.repository.mappers.AlcoholicMapper;
import com.alcosystem.alcosystem.repository.mappers.BedAveragePassesMapper;
import com.alcosystem.alcosystem.repository.mappers.BedColorMapper;
import com.alcosystem.alcosystem.repository.mappers.BedMapper;
import com.alcosystem.alcosystem.repository.mappers.DrinkMapper;
import com.alcosystem.alcosystem.repository.mappers.DrinkQuantityMapper;
import com.alcosystem.alcosystem.repository.mappers.InspectorMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MainRepository {

    private final JdbcTemplate jdbcTemplate;

    public MainRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Inspector> request1(Long alcoid, Long quantity, String from, String to) {
        String query = "SELECT inspector.id as id, inspector.name as name, inspector.hire_date as hire_date, inspector.department_id as department_id, COUNT(inspector.id) as taken_quantity " +
                "FROM alcoholic_taken " +
                "INNER JOIN inspector ON inspector.id = inspector_id " +
                "WHERE alco_id = " + alcoid + " AND date_taken BETWEEN '" + from + "' AND '" + to +"' " +
                "GROUP BY inspector.id " +
                "HAVING COUNT(inspector.id) >= " + quantity + ";";
        return jdbcTemplate.query(query, new InspectorMapper());
    }

    public List<Bed> request2(Long alcoid, String from, String to) {
        String query = "SELECT bed.id as id, bed.color as color, bed.size as size " +
                "FROM (SELECT alco_id, bed_id, date_changed as d FROM alcoholic_bed_changed UNION SELECT alco_id, bed_id, date_taken as d FROM alcoholic_taken) as foo " +
                "INNER JOIN bed ON bed.id = bed_id " +
                "WHERE alco_id = " + alcoid +" AND d BETWEEN '" + from + "' AND '" + to + "' " +
                "GROUP BY bed.id;";
        return jdbcTemplate.query(query, new BedMapper());
    }

    public List<Alcoholic> request3(Long Inspectorid, Long quantity, String from, String to) {
        String query = "SELECT alcoholic.id as id, alcoholic.first_name as first_name, alcoholic.last_name as last_name, alcoholic.birth_date " +
                "FROM alcoholic_taken " +
                "INNER JOIN alcoholic ON alcoholic.id = alco_id " +
                "WHERE alcoholic_taken.inspector_id = "+ Inspectorid +" AND date_taken BETWEEN '" + from + "' AND '" + to + "' " +
                "GROUP BY alcoholic.id " +
                "HAVING COUNT(alco_id) >= " + quantity + ";";
        return jdbcTemplate.query(query, new AlcoholicMapper());
    }

    public List<Bed> request4(Long alcoid, String from, String to) {
        String query = "SELECT bed_id as id, bed.color as color, bed.size as size " +
                "FROM alcoholic_escaped " +
                "INNER JOIN bed ON bed.id = bed_id " +
                "WHERE alco_id = " + alcoid + " AND date_escaped BETWEEN '" + from + "' AND '" + to + "';";
        return jdbcTemplate.query(query, new BedMapper());
    }

    public List<Inspector> request5(Long alcoid) {
        String query = "SELECT inspector_id as id, inspector.name as name, inspector.hire_date as hire_date, inspector.department_id as department_id " +
                "FROM " +
                "  ((SELECT alco_id, inspector_id, COUNT(*) AS taken_count " +
                "    FROM alcoholic_taken " +
                "   GROUP BY alco_id, inspector_id) AS t " +
                "  FULL OUTER JOIN " +
                "  (SELECT alco_id, inspector_id, COUNT(*) AS release_count " +
                "   FROM alcoholic_released " +
                "   GROUP BY alco_id, inspector_id) AS r " +
                "  USING(alco_id, inspector_id) INNER JOIN inspector ON inspector.id = inspector_id) " +
                "WHERE coalesce(release_count, 0) > coalesce(taken_count, 0) AND alco_id=" + alcoid +"; ";
        return jdbcTemplate.query(query, new InspectorMapper());
    }

    public List<Inspector> request6(Long quantity, String from, String to) {
        String query = "SELECT inspector.id as id, inspector.name as name, inspector.hire_date as hire_date, inspector.department_id as department_id, COUNT(DISTINCT alco_id) as alcoholic_taken " +
                "FROM alcoholic_taken " +
                "INNER JOIN inspector ON inspector_id = inspector.id " +
                "WHERE date_taken BETWEEN '" + from + "' AND '" + to + "' " +
                "GROUP BY inspector.id " +
                "HAVING COUNT(alco_id) >= " + quantity + ";";
        return jdbcTemplate.query(query, new InspectorMapper());
    }

    public List<Alcoholic> request7(Long quantity, String from, String to) {
        String query = "SELECT alcoholic.id as id, alcoholic.first_name as first_name, alcoholic.last_name as last_name, alcoholic.birth_date as birth_date " +
                "FROM alcoholic_taken " +
                "INNER JOIN alcoholic ON alco_id = alcoholic.id " +
                "WHERE date_taken BETWEEN '" + from + "' AND '" + to + "' " +
                "GROUP BY alcoholic.id " +
                "HAVING COUNT(alco_id) >= " + quantity + ";";
        return jdbcTemplate.query(query, new AlcoholicMapper());
    }

    public List<Action> request8(Long alcoid, Long inspectorid, String from, String to) {
        String query = "SELECT action_date, action FROM\n" +
                "(SELECT alco_id, inspector_id, date_taken AS action_date, 'Taken to centre' AS action\n" +
                "FROM alcoholic_taken\n" +
                "UNION\n" +
                "SELECT alco_id, inspector_id, date_released AS action_date, 'Released from centre' AS action\n" +
                "FROM alcoholic_released\n" +
                "UNION\n" +
                "SELECT alco_id, inspector_id, date_changed AS action_date, 'Changed bed' AS action\n" +
                "FROM alcoholic_bed_changed) AS a\n" +
                "WHERE alco_id=" + alcoid + " AND\n" +
                "    inspector_id=" + inspectorid + " AND\n" +
                "    action_date > '" + from + "' AND\n" +
                "    action_date < '" + to + "';";
        return jdbcTemplate.query(query, new ActionMapper());
    }

    public List<DrinkQuantity> request9(Long alcoid, Long quantity, String from, String to) {
        String query = "SELECT drinks.id as id, drinks.name as name, count(group_of.drink_id) as quantity\n" +
                "FROM (((alcoholic\n" +
                "INNER JOIN group_alcoholic ON group_alcoholic.alco_id = alcoholic.id)\n" +
                "INNER JOIN group_of ON group_alcoholic.group_id = group_of.id)\n" +
                "INNER JOIN drinks ON drinks.id = group_of.drink_id)\n" +
                "WHERE alcoholic.id = " + alcoid + " AND date_drank BETWEEN '" + from + "' AND '" + to + "'\n" +
                "GROUP BY alcoholic.id, drinks.id\n" +
                "HAVING count(group_of.drink_id) >= " + quantity + ";";
        return jdbcTemplate.query(query, new DrinkQuantityMapper());
    }

    public Map<String, Long> request10() {
        String query = "SELECT TO_CHAR( " +
                "TO_DATE (EXTRACT(MONTH FROM alcoholic_escaped.date_escaped)::text, 'MM'), 'Month' " +
                ") as m, count(EXTRACT(MONTH FROM alcoholic_escaped.date_escaped)) as quantity " +
                "FROM alcoholic_escaped " +
                "GROUP BY m;";
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(query);
        return mapList.stream().collect(Collectors.toMap(k -> (String) k.get("m"), k -> (Long) k.get("quantity")));
    }

    public List<BedAveragePasses> request11(Long inspectorid, String from, String to) {
        String query = "SELECT bed_id as id, ROUND(AVG(passed_amount), 3) AS average_passes FROM\n" +
                "  (SELECT bed_id,\n" +
                "       alco_id,\n" +
                "       COUNT(date_passed) AS passed_amount\n" +
                "  FROM\n" +
                "    ((SELECT DISTINCT alco_id, bed_id\n" +
                "     FROM\n" +
                "      (SELECT inspector_id, alco_id, bed_id, date_taken AS bed_date FROM alcoholic_taken AS t\n" +
                "      UNION\n" +
                "      SELECT inspector_id, alco_id, bed_id, date_changed AS bed_date FROM alcoholic_bed_changed) AS c\n" +
                "      WHERE inspector_id=1 AND\n" +
                "          bed_date > '" + from + "' AND\n" +
                "          bed_date < '" + to + "') AS b\n" +
                "    LEFT JOIN alcoholic_passed USING(alco_id, bed_id)) AS p\n" +
                "  GROUP BY bed_id, alco_id) AS x\n" +
                "GROUP BY bed_id ORDER BY AVG(passed_amount) DESC;";
        return jdbcTemplate.query(query, new BedAveragePassesMapper());
    }

    public List<DrinkQuantity> request12(Long alcoid, String from, String to) {
        String query = "SELECT drinks.id as id, drinks.name as name, (count(drinks.id) - 1) as quantity FROM\n" +
                "((( (SELECT DISTINCT group_alcoholic.group_id as ga\n" +
                "FROM group_alcoholic\n" +
                "WHERE group_alcoholic.alco_id = " + alcoid + ") as foo\n" +
                "INNER JOIN group_alcoholic ON group_alcoholic.group_id = ga)\n" +
                "INNER JOIN group_of ON group_of.id = group_alcoholic.group_id)\n" +
                "INNER JOIN drinks ON drinks.id = group_of.drink_id)\n" +
                "WHERE group_of.date_drank BETWEEN '" + from + "' AND '" + to + "'\n" +
                "GROUP BY drinks.id\n" +
                "ORDER BY quantity DESC;";
        return  jdbcTemplate.query(query, new DrinkQuantityMapper());
    }

    public List<AlcoPerDepartment> request13(String from, String to) {
        String query = "SELECT department.name as department_name, count(department.name) as alco_quantity " +
                "FROM ((alcoholic_taken " +
                "INNER JOIN inspector ON inspector.id = alcoholic_taken.inspector_id) " +
                "INNER JOIN department ON department.id = inspector.department_id) " +
                "WHERE alcoholic_taken.date_taken BETWEEN '" + from + "' AND '" + to + "' " +
                "GROUP BY department.name " +
                "ORDER BY alco_quantity DESC;";
        return jdbcTemplate.query(query, new AlcoPerDepartmentMapper());
    }

    public List<BedColorQuantity> request14() {
        String query = "SELECT bed.color as bed_color, COUNT(bed.color) as color_quantity " +
                "FROM bed " +
                "INNER JOIN alcoholic_passed on alcoholic_passed.bed_id = bed.id " +
                "GROUP BY bed.color " +
                "ORDER BY count(bed.color) DESC;";
        return jdbcTemplate.query(query, new BedColorMapper());
    }

    public List<Drink> updateDrink(Drink drink) {
        String query = "UPDATE drink SET name=" + drink.getDrinkName() + ", degree=" + drink.getDegree() + " WHERE id=" + drink.getId() + ";";
        jdbcTemplate.update(query);
        return jdbcTemplate.query("SELECT * FROM drink WHERE id=" + drink.getId(), new DrinkMapper());
    }

    public void delete(Long id) {
        String query = "DELETE drink WHERE id=" + id;
        jdbcTemplate.update(query);
    }

}
