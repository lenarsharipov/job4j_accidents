package ru.job4j.accidents.service.mem;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;
import ru.job4j.accidents.service.RuleService;

import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class RuleServiceMem implements RuleService {

    private final RuleRepository ruleRepository;

    @Override
    public Collection<Rule> findAll() {
        return ruleRepository.findAll();
    }

    @Override
    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }
}
