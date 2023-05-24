package ru.job4j.accidents.service.data;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.data.AccidentData;
import ru.job4j.accidents.repository.data.AccidentTypeData;
import ru.job4j.accidents.repository.data.RuleData;
import ru.job4j.accidents.service.AccidentService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Service
@AllArgsConstructor
@ThreadSafe
public class AccidentServiceData implements AccidentService {
    private final AccidentData accidentRepository;
    private final AccidentTypeData accidentTypeRepository;
    private final RuleData ruleRepository;

    private List<Integer> parseRIds(String[] rIds) {
        return Arrays.stream(rIds)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Accident> save(Accident accident, String[] rIds) {
        Optional<Accident> result = Optional.empty();
        var typeOptional = accidentTypeRepository.findById(accident.getType().getId());
        var rules = ruleRepository.findByIdIn(parseRIds(rIds));
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
        var rules = ruleRepository.findByIdIn(parseRIds(rIds));
        if (typeOptional.isPresent() && !rules.isEmpty()) {
            accident.setRules(rules);
            accident.setType(typeOptional.get());
            accidentRepository.save(accident);
            result = true;
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
