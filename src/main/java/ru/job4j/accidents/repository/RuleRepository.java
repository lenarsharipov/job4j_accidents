package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface RuleRepository {
    Collection<Rule> findAll();

    Optional<Rule> findById(int id);

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
