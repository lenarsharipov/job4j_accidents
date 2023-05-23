package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.*;

@Primary
@Repository
@AllArgsConstructor
@ThreadSafe
public class RuleHibernate implements RuleRepository {
    private static final Logger LOG =
            LoggerFactory.getLogger(RuleHibernate.class.getName());
    private final CrudRepository crudRepository;
    private static final String ID = "fId";
    private static final String FIND_ALL = """
            FROM Rule r
            ORDER BY r.id
            """;
    private static final String FIND_BY_ID = """
            FROM Rule r
            WHERE r.id = :fId
            """;

    @Override
    public Collection<Rule> findAll() {
        List<Rule> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Rule.class);
        } catch (Exception exception) {
            LOG.error("UNABLE TO LIST RULES", exception);
        }
        return result;
    }

    @Override
    public Optional<Rule> findById(int id) {
        Optional<Rule> result = Optional.empty();
        try {
            result = crudRepository.optional(
                    FIND_BY_ID, Rule.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error("UNABLE TO FIND RULE BY ID", exception);
        }
        return result;
    }
}
