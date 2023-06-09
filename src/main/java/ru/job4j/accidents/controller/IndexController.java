package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.AccidentService;

@ThreadSafe
@Controller
@AllArgsConstructor
public class IndexController {

    private final AccidentService accidentService;
    private static final String USER = "user";
    private static final String ACCIDENTS = "accidents";
    private static final String INDEX_PAGE = "index";

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        var user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute(USER, user);
        model.addAttribute(ACCIDENTS, accidentService.findAll());
        return INDEX_PAGE;
    }
}
