package ru.job4j.accidents.service.jdbc;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.jdbc.AccidentJdbcTemplate;
import ru.job4j.accidents.repository.jdbc.AccidentTypeJdbcTemplate;
import ru.job4j.accidents.repository.jdbc.RuleJdbcTemplate;
import ru.job4j.accidents.service.AccidentService;

import java.util.Collection;
import java.util.Optional;

@Service
@Primary
@ThreadSafe
@AllArgsConstructor
public class AccidentServiceJdbc implements AccidentService {
    private final AccidentJdbcTemplate accidentRepository;
    private final AccidentTypeJdbcTemplate accidentTypeRepository;
    private final RuleJdbcTemplate ruleRepository;

    @Override
    public Optional<Accident> save(Accident accident, String[] rIds) {
        Optional<Accident> result = Optional.empty();
        var typeOptional = accidentTypeRepository.findById(accident.getType().getId());
        var rules = ruleRepository.findSelected(rIds);
        if (typeOptional.isPresent() && !rules.isEmpty()) {
            accident.setRules(rules);
            accident.setType(typeOptional.get());
            accidentRepository.save(accident);
            result = Optional.of(accident);
        }
        return result;
    }

    @Override
    public boolean update(Accident accident, String[] rIds) {
        var result = false;
        var typeOptional = accidentTypeRepository.findById(accident.getType().getId());
        var rules = ruleRepository.findSelected(rIds);
        if (typeOptional.isPresent() && !rules.isEmpty()) {
            accident.setRules(rules);
            accident.setType(typeOptional.get());
            result = accidentRepository.update(accident);
        }
        return result;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidentRepository.findAll();
    }
}
