package ru.job4j.accidents.repository.jdbc;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@Repository
@ThreadSafe
@AllArgsConstructor
public class AccidentTypeJdbcTemplate implements AccidentTypeRepository {
    private final JdbcTemplate jdbc;

    @Override
    public List<AccidentType> findAll() {
        return jdbc.query("SELECT * FROM types",
                (rs, row) -> new AccidentType(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return jdbc.query("SELECT * FROM types WHERE id = ?",
                (rs, row) -> new AccidentType(
                        rs.getInt("id"),
                        rs.getString("name")), id).stream().findFirst();
    }
}
