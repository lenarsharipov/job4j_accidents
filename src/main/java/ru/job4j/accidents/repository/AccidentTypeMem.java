package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class AccidentTypeMem implements AccidentTypeRepository {
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(1);

    public AccidentTypeMem() {
        types.put(id.get(), new AccidentType(id.getAndIncrement(), "Two Cars"));
        types.put(id.get(), new AccidentType(id.getAndIncrement(), "Car and Pedestrian"));
        types.put(id.get(), new AccidentType(id.getAndIncrement(), "Car and Bicycle"));
    }

    @Override
    public List<AccidentType> findAll() {
        return new ArrayList<>(types.values());
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(types.get(id));
    }
}
