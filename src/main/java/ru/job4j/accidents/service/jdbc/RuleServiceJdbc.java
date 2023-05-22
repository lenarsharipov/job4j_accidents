package ru.job4j.accidents.service.jdbc;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.jdbc.RuleJdbcTemplate;
import ru.job4j.accidents.service.RuleService;

import java.util.Collection;
import java.util.Optional;

@Service
@Primary
@AllArgsConstructor
@ThreadSafe
public class RuleServiceJdbc implements RuleService {
    private final RuleJdbcTemplate ruleRepository;

    @Override
    public Collection<Rule> findAll() {
        return ruleRepository.findAll();
    }

    @Override
    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }
}
