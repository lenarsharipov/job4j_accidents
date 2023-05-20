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
    private final List<AccidentType> types = new ArrayList<>();
    private final AtomicInteger id = new AtomicInteger(1);

    public AccidentMem() {
        types.add(new AccidentType(1, "Two cars"));
        types.add(new AccidentType(2, "Car and pedestrian"));
        types.add(new AccidentType(3, "Car and bicycle"));

        accidents.put(id.get(), new Accident(id.getAndIncrement(), "accident1", types.get(0), "car1 hit car2", "Moscow, N* street"));
        accidents.put(id.get(), new Accident(id.getAndIncrement(), "accident2", types.get(1), "car hit pedestrian", "Moscow, M* street"));
        accidents.put(id.get(), new Accident(id.getAndIncrement(), "accident3", types.get(2), "car hit bus stop", "Moscow, K* street"));
    }

    private AccidentType getTypeById(int id) {
        AccidentType result = null;
        for (var type : types) {
            if (type.getId() == id) {
                result = type;
                break;
            }
        }
        return result;
    }

    @Override
    public Accident create(Accident accident) {
        accident.setType(getTypeById(accident.getType().getId()));
        accident.setId(id.get());
        accidents.put(id.getAndIncrement(), accident);
        return accident;
    }

    @Override
    public boolean update(Accident accident) {
        accident.setType(getTypeById(accident.getType().getId()));
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
