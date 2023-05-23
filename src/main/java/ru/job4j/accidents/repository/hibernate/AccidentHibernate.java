package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.*;

@Repository
@AllArgsConstructor
@ThreadSafe
public class AccidentHibernate implements AccidentRepository {
    private static final Logger LOG =
            LoggerFactory.getLogger(AccidentHibernate.class.getName());

    private final CrudRepository crudRepository;
    private static final String ID = "fId";
    private static final String FIND_ALL = """
            SELECT DISTINCT a
            FROM Accident a
            LEFT JOIN FETCH a.rules
            ORDER BY a.id
            """;
    private static final String FIND_BY_ID = """
            SELECT DISTINCT a
            FROM Accident a
            LEFT JOIN FETCH a.rules
            WHERE a.id = :fId
            """;

    @Override
    public Optional<Accident> save(Accident accident) {
        Optional<Accident> result = Optional.empty();
        try {
            crudRepository.run(session -> session.save(accident));
            result = Optional.of(accident);
        } catch (Exception exception) {
            LOG.error("UNABLE TO SAVE ACCIDENT", exception);
        }
        return result;
    }

    @Override
    public Optional<Accident> findById(int id) {
        Optional<Accident> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, Accident.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error("UNABLE TO FIND ACCIDENT BY ID", exception);
        }
        return result;
    }

    @Override
    public Collection<Accident> findAll() {
        List<Accident> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Accident.class);
        } catch (Exception exception) {
            LOG.error("UNABLE TO LIST ALL ACCIDENTS", exception);
        }
        return result;
    }

    @Override
    public boolean update(Accident accident) {
        var result = true;
        try {
            crudRepository.run(session -> session.update(accident));
        } catch (Exception exception) {
            result = false;
            LOG.error("UNABLE TO UPDATE ACCIDENT", exception);
        }
        return result;
    }
}
