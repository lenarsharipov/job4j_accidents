package ru.job4j.accidents.service.jdbc;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.jdbc.AccidentTypeJdbcTemplate;
import ru.job4j.accidents.service.AccidentTypeService;

import java.util.Collection;
import java.util.Optional;

@Service
@Primary
@AllArgsConstructor
@ThreadSafe
public class AccidentTypeServiceJdbc implements AccidentTypeService {
    private final AccidentTypeJdbcTemplate accidentTypeRepository;

    @Override
    public Collection<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }
}
