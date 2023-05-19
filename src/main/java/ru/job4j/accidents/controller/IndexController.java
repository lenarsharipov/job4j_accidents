package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@ThreadSafe
@Controller
@AllArgsConstructor
public class IndexController {

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        return "index";
    }
}
