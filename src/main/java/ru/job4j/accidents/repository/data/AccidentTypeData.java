package ru.job4j.accidents.repository.data;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;

@Primary
@Repository
public interface AccidentTypeData extends CrudRepository<AccidentType, Integer> {
    @Query(
            """
            FROM AccidentType a
            ORDER BY a.id
            """
    )
    Collection<AccidentType> findAll();
}
