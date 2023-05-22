package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentService {
    Optional<Accident> create(Accident accident, String[] rIds);

    boolean update(Accident accident, String[] rIds);

    Optional<Accident> findById(int id);

    List<Accident> findAll();

}
