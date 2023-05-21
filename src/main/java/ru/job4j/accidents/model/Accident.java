package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Accident {
    @EqualsAndHashCode.Include
    private Integer id;

    private String name;
    private AccidentType type;
    private Set<Rule> rules;
    private String text;
    private String address;

    public String rules() {
        return rules.stream()
                .map(Rule::getName)
                .collect(Collectors.joining(", "));
    }
}
