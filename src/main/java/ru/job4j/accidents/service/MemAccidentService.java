package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@ThreadSafe
public class MemAccidentService implements AccidentService {

    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository accidentTypeRepository;
    private final RuleRepository ruleRepository;

    @Override
    public Optional<Accident> create(Accident accident, String[] rIds) {
        Optional<Accident> result = Optional.empty();
        var typeOptional = accidentTypeRepository.findById(accident.getType().getId());
        var rules = ruleRepository.findSelected(rIds);
        if (typeOptional.isPresent() && !rules.isEmpty()) {
            accident.setRules(rules);
            accident.setType(typeOptional.get());
            accidentRepository.create(accident);
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
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }
}
