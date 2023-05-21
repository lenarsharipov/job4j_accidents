package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;

public interface RuleService {
    List<Rule> findAll();

    Optional<Rule> findById(int id);
}
