package ru.job4j.accidents.repository.mem;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class AccidentMem implements AccidentRepository {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(1);

    public AccidentMem() {
        var rule1 = new Rule(1, "Rule 1");
        var rule2 = new Rule(2, "Rule 2");
        var rule3 = new Rule(3, "Rule 3");

        accidents.put(
                id.get(),
                new Accident(
                        id.getAndIncrement(), "accident1",
                        new AccidentType(1, "Two Cars"),
                        Set.of(rule1), "car1 hit car2", "Moscow, N* street")
        );
        accidents.put(
                id.get(),
                new Accident(id.getAndIncrement(), "accident2",
                        new AccidentType(2, "Car and Pedestrian"),
                        Set.of(rule1, rule3), "car hit pedestrian", "Moscow, M* street")
        );
        accidents.put(
                id.get(),
                new Accident(
                        id.getAndIncrement(), "accident3",
                        new AccidentType(3, "Car and Bicycle"),
                        Set.of(rule1, rule2, rule3), "car hit bicycle", "Moscow, K* street")
        );
    }

    @Override
    public Optional<Accident> save(Accident accident) {
        accident.setId(id.get());
        accidents.put(id.getAndIncrement(), accident);
        return Optional.of(accident);
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(
                accident.getId(), (id, oldAccident) -> new Accident(
                        oldAccident.getId(),
                        accident.getName(),
                        accident.getType(),
                        accident.getRules(),
                        accident.getText(),
                        accident.getAddress()
                )) != null;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(
                accidents.get(id)
        );
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }

}
