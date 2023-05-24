package ru.job4j.accidents.repository.data;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Authority;

public interface AuthorityData extends CrudRepository<Authority, Integer> {
    Authority findByAuthority(String authority);

}
