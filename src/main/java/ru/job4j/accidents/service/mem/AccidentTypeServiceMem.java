package ru.job4j.accidents.service.mem;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;
import ru.job4j.accidents.service.AccidentTypeService;

import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Service
public class AccidentTypeServiceMem implements AccidentTypeService {
    private final AccidentTypeRepository accidentTypeRepository;

    @Override
    public Collection<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }
}
