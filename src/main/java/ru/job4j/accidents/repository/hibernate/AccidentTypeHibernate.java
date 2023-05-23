package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.*;

@ThreadSafe
@AllArgsConstructor
@Repository
@Primary
public class AccidentTypeHibernate implements AccidentTypeRepository {
    private static final Logger LOG =
            LoggerFactory.getLogger(AccidentTypeHibernate.class.getName());

    private final CrudRepository crudRepository;
    private static final String ID = "fId";
    private static final String FIND_ALL = """
            FROM AccidentType a
            ORDER BY a.id
            """;

    private static final String FIND_BY_ID = """
            SELECT DISTINCT a
            FROM AccidentType a
            WHERE a.id = :fId
            """;

    @Override
    public Collection<AccidentType> findAll() {
        List<AccidentType> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, AccidentType.class);
        } catch (Exception exception) {
            LOG.error("UNABLE TO LIST ACCIDENT TYPES", exception);
        }
        return result;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        Optional<AccidentType> result = Optional.empty();
        try {
            result = crudRepository.optional(
                    FIND_BY_ID, AccidentType.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error("UNABLE TO FIND ACCIDENT TYPE BY ID", exception);
        }
        return result;
    }
}
