package ru.job4j.accidents.repository.data;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;

@Primary
@Repository
public interface AccidentData extends CrudRepository<Accident, Integer> {
    @Query(
            """
            SELECT DISTINCT a
            FROM Accident a
            LEFT JOIN FETCH a.rules
            ORDER BY a.id
            """
    )
    Collection<Accident> findAll();
}
