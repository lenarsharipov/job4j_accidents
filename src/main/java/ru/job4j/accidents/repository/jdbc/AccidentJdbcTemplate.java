package ru.job4j.accidents.repository.jdbc;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
@Primary
@ThreadSafe
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {
    private final JdbcTemplate jdbc;

    private final RowMapper<Accident> accidentRowMapper = (rs, row) -> {
        Accident accident = new Accident();
        accident.setId(rs.getInt("aId"));
        accident.setName(rs.getString("aName"));
        accident.setText(rs.getString("aText"));
        accident.setAddress(rs.getString("aAddress"));
        accident.setType(new AccidentType(
                rs.getInt("tId"),
                rs.getString("tName")));
        return accident;
    };

    private final RowMapper<Rule> ruleRowMapper = (resultSet, row) -> {
        Rule rule = new Rule();
        rule.setId(resultSet.getInt("rId"));
        rule.setName(resultSet.getString("rName"));
        return rule;
    };

    private final ResultSetExtractor<Map<Integer, Accident>> extractor = (resultSet) -> {
        Map<Integer, Accident> result = new HashMap<>();
        while (resultSet.next()) {
            Accident accident = new Accident(
                    resultSet.getInt("aId"),
                    resultSet.getString("aName"),
                    new AccidentType(
                            resultSet.getInt("tId"),
                            resultSet.getString("tName")),
                    new HashSet<>(),
                    resultSet.getString("aText"),
                    resultSet.getString("aAddress")
            );
            result.putIfAbsent(accident.getId(), accident);
            result.get(accident.getId()).getRules().add(new Rule(
                    resultSet.getInt("rId"),
                    resultSet.getString("rName")
            ));
        }
        return result;
    };

    @Override
    public Optional<Accident> save(Accident accident) {
        var sql = """
                INSERT INTO accidents (name, text, address, type_id)
                VALUES (?, ?, ?, ?)
                """;
        var keyHolder = new GeneratedKeyHolder();

        jdbc.update(
                connection -> {
                            PreparedStatement ps = connection
                                    .prepareStatement(sql, new String[] {"id"});
                ps.setString(1, accident.getName());
                ps.setString(2, accident.getText());
                ps.setString(3, accident.getAddress());
                ps.setInt(4, accident.getType().getId());
                return ps;
            }, keyHolder);
        accident.setId(keyHolder.getKey().intValue());

        for (var rule : accident.getRules()) {
            jdbc.update(
                    """
                    INSERT INTO accidents_rules (accident_id, rule_id)
                    VALUES (?, ?)
                    """,
                    accident.getId(),
                    rule.getId()
            );
        }
        return Optional.of(accident);
    }

    @Override
    public boolean update(Accident accident) {
        var result = jdbc.update("""
                UPDATE accidents
                SET name = ?, text = ?, address = ?, type_id = ?
                WHERE id = ?
                """,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId()) > 0;

        jdbc.update("DELETE FROM accidents_rules WHERE accident_id = ?", accident.getId());
        for (var rule : accident.getRules()) {
            jdbc.update(
                    """
                    INSERT INTO accidents_rules (accident_id, rule_id)
                    VALUES (?, ?)
                    """,
                    accident.getId(),
                    rule.getId()
            );
        }
        return result;
    }

    @Override
    public Collection<Accident> findAll() {
        Collection<Accident> result = jdbc.query(
                """
                SELECT a.id aId, a.name aName,
                a.text aText, a.address aAddress,
                t.id tId, t.name tName,
                r.id rId, r.name rName
                FROM accidents a
                JOIN types t
                ON a.type_id = t.id
                JOIN accidents_rules ar ON a.id = ar.accident_id
                JOIN rules r ON r.id = ar.rule_id
                """, extractor).values();
        return result.size() > 0 ? result : Collections.emptyList();
    }

    @Override
    public Optional<Accident> findById(int id) {
        Accident accident = jdbc.queryForObject(
                """
                SELECT a.id aId, a.name aName,
                a.text aText, a.address aAddress,
                t.id tId, t.name tName
                FROM accidents a
                LEFT JOIN types t ON a.type_id = t.id
                WHERE a.id = ?
                """, accidentRowMapper, id);

        List<Rule> rules = jdbc.query("""
                SELECT r.id rid, r.name rName
                FROM accidents a
                LEFT JOIN accidents_rules ar ON a.id = ar.accident_id
                LEFT JOIN rules r ON r.id = ar.rule_id
                WHERE a.id = ?
                """, ruleRowMapper, id);
        accident.setRules(new HashSet<>(rules));
        return Optional.of(accident);
    }
}