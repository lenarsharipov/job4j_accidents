package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
@AllArgsConstructor
public class AccidentMem implements AccidentRepository {
    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger(1);

    public AccidentMem() {
        accidents.put(id.get(), new Accident(id.getAndIncrement(), "accident1", "car1 hit car2", "Moscow, N* street"));
        accidents.put(id.get(), new Accident(id.getAndIncrement(), "accident2", "car1 hit pedestrian", "Moscow, M* street"));
        accidents.put(id.get(), new Accident(id.getAndIncrement(), "accident3", "car1 hit bus stop", "Moscow, K* street"));
        accidents.put(id.get(), new Accident(id.getAndIncrement(), "accident4", "bus hit car1", "Moscow, L* street"));
    }

    @Override
    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

}
