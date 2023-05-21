package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class AccidentMem implements AccidentRepository {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(1);

    public AccidentMem() {
        accidents.put(
                id.get(),
                new Accident(
                        id.getAndIncrement(), "accident1",
                        new AccidentType(1, "Two Cars"),
                        "car1 hit car2", "Moscow, N* street")
        );
        accidents.put(
                id.get(),
                new Accident(id.getAndIncrement(), "accident2",
                        new AccidentType(2, "Car and Pedestrian"),
                        "car hit pedestrian", "Moscow, M* street")
        );
        accidents.put(
                id.get(),
                new Accident(
                        id.getAndIncrement(), "accident3",
                        new AccidentType(3, "Car and Bicycle"),
                        "car hit bus stop", "Moscow, K* street"));
    }

    @Override
    public Optional<Accident> create(Accident accident) {
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
    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

}
