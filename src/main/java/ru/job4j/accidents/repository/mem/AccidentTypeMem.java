package ru.job4j.accidents.repository.mem;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class AccidentTypeMem implements AccidentTypeRepository {
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();

    public AccidentTypeMem() {
        AtomicInteger id = new AtomicInteger(1);
        types.put(id.get(), new AccidentType(id.getAndIncrement(), "Two Cars"));
        types.put(id.get(), new AccidentType(id.getAndIncrement(), "Car and Pedestrian"));
        types.put(id.get(), new AccidentType(id.getAndIncrement(), "Car and Bicycle"));
    }

    @Override
    public Collection<AccidentType> findAll() {
        return types.values();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(types.get(id));
    }
}
