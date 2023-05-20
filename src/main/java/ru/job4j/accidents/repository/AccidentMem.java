package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

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
    private final AccidentTypeRepository types;

    public AccidentMem(AccidentTypeRepository types) {
        this.types = types;
        accidents.put(id.get(), new Accident(id.getAndIncrement(), "accident1", types.findById(1).get(), "car1 hit car2", "Moscow, N* street"));
        accidents.put(id.get(), new Accident(id.getAndIncrement(), "accident2", types.findById(2).get(), "car hit pedestrian", "Moscow, M* street"));
        accidents.put(id.get(), new Accident(id.getAndIncrement(), "accident3", types.findById(3).get(), "car hit bus stop", "Moscow, K* street"));
    }

    @Override
    public Accident create(Accident accident) {
        accident.setType(types.findById(accident.getType().getId()).get());
        accident.setId(id.get());
        accidents.put(id.getAndIncrement(), accident);
        return accident;
    }

    @Override
    public boolean update(Accident accident) {
        accident.setType(types.findById(accident.getType().getId()).get());
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
