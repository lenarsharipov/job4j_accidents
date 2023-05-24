package ru.job4j.accidents.repository.data;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.List;
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

    Set<Rule> findByIdIn(List<Integer> rIds);
}
