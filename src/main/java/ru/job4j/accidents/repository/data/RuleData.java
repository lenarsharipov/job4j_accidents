package ru.job4j.accidents.repository.data;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Primary
@Repository
public interface RuleData extends CrudRepository<Rule, Integer> {
    @Query(
            """
            SELECT DISTINCT r
            FROM Rule r
            ORDER BY r.id
            """
    )
    Collection<Rule> findAll();

    default Set<Rule> findSelected(String[] rIds) {
        Set<Rule> rules = new HashSet<>();
        for (var rId : rIds) {
            var id = Integer.parseInt(rId);
            var rule = findById(id);
            rule.ifPresent(rules::add);
        }
        return rules;
    }
}
