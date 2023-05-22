package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class RuleMem implements RuleRepository {
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();
    private AtomicInteger ruleId = new AtomicInteger(1);

    public RuleMem() {
        rules.put(ruleId.get(), new Rule(ruleId.getAndIncrement(), "Rule 1"));
        rules.put(ruleId.get(), new Rule(ruleId.getAndIncrement(), "Rule 2"));
        rules.put(ruleId.get(), new Rule(ruleId.getAndIncrement(), "Rule 3"));
    }

    @Override
    public Set<Rule> findSelected(String[] rIds) {
        Set<Rule> rules = new HashSet<>();
        for (var rId : rIds) {
            var id = Integer.parseInt(rId);
            var rule = findById(id);
            rule.ifPresent(rules::add);
        }
        return rules;
    }

    @Override
    public List<Rule> findAll() {
        return new ArrayList<>(rules.values());
    }

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
    }

}
