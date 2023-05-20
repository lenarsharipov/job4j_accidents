package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

public interface AccidentTypeRepository {
    List<AccidentType> findAll();

    Optional<AccidentType> findById(int id);
}
