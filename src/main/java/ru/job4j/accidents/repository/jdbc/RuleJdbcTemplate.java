package ru.job4j.accidents.repository.jdbc;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;
import java.util.Optional;

@Repository
@ThreadSafe
@AllArgsConstructor
public class RuleJdbcTemplate implements RuleRepository {
    private final JdbcTemplate jdbc;

    @Override
    public Collection<Rule> findAll() {
        return jdbc.query("SELECT * FROM rules",
                (rs, row) -> new Rule(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
    }

    @Override
    public Optional<Rule> findById(int id) {
        return jdbc.query("SELECT * FROM rules WHERE id = ?",
                (rs, row) -> new Rule(
                        rs.getInt("id"),
                        rs.getString("name")), id).stream().findFirst();
    }
}
