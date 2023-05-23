package ru.job4j.accidents.service.hibernate;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.hibernate.AccidentTypeHibernate;
import ru.job4j.accidents.service.AccidentTypeService;

import java.util.Collection;
import java.util.Optional;

@Primary
@Service
@AllArgsConstructor
@ThreadSafe
public class AccidentTypeServiceHibernate implements AccidentTypeService {
    private final AccidentTypeHibernate accidentTypeRepository;

    @Override
    public Collection<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }
}
