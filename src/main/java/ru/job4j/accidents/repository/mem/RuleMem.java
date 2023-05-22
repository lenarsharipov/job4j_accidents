package ru.job4j.accidents.repository.mem;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class RuleMem implements RuleRepository {
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    public RuleMem() {
        AtomicInteger ruleId = new AtomicInteger(1);
        rules.put(ruleId.get(), new Rule(ruleId.getAndIncrement(), "Rule 1"));
        rules.put(ruleId.get(), new Rule(ruleId.getAndIncrement(), "Rule 2"));
        rules.put(ruleId.get(), new Rule(ruleId.getAndIncrement(), "Rule 3"));
    }

    @Override
    public Collection<Rule> findAll() {
        return rules.values();
    }

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
    }

}
