package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.AccidentService;

@ThreadSafe
@Controller
@AllArgsConstructor
public class IndexController {

    private final AccidentService accidentService;
    private static final String USER_VALUE = "TEST USER";
    private static final String USER_ATTRIBUTE = "user";
    private static final String ACCIDENTS_ATTRIBUTE = "accidents";
    private static final String INDEX_PAGE = "index";

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        model.addAttribute(USER_ATTRIBUTE, USER_VALUE);
        model.addAttribute(ACCIDENTS_ATTRIBUTE, accidentService.findAll());
        return INDEX_PAGE;
    }
}
