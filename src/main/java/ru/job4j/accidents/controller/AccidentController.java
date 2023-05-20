package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@ThreadSafe
@Controller
@AllArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;
    private static final String MESSAGE = "message";
    private static final String USER_VALUE = "Lenar Sharipov";
    private static final String UNABLE_TO_UPDATE = "UNABLE TO UPDATE SPECIFIED ACCIDENT";
    private static final String UNABLE_TO_FIND_BY_ID = "UNABLE TO FIND ACCIDENT BY SPECIFIED ID";
    private static final String ERROR_404_PAGE = "404";
    private static final String REDIRECT_INDEX_PAGE = "redirect:/index";
    private static final String CREATE_ACCIDENT_PAGE = "createAccident";
    private static final String UPDATE_ACCIDENT_PAGE = "updateAccident";
    private static final String ACCIDENT_ATTRIBUTE = "accident";
    private static final String USER_ATTRIBUTE = "user";

    /**
     * Get Accident creation page.
     * @return createAccident page.
     */
    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute(USER_ATTRIBUTE, USER_VALUE);
        return CREATE_ACCIDENT_PAGE;
    }

    /**
     * Save new accident.
     * @param accident Accident.
     * @return 404 or redirect:/accidents.
     */
    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidentService.create(accident);
        return REDIRECT_INDEX_PAGE;
    }

    @GetMapping("/formUpdateAccident")
    public String viewUpdate(@RequestParam("id") int id, Model model) {
        var accidentOptional = accidentService.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute(USER_ATTRIBUTE, USER_VALUE);
            model.addAttribute(MESSAGE, UNABLE_TO_FIND_BY_ID);
            return ERROR_404_PAGE;
        }
        model.addAttribute(ACCIDENT_ATTRIBUTE, accidentOptional.get());
        model.addAttribute(USER_ATTRIBUTE, USER_VALUE);
        return UPDATE_ACCIDENT_PAGE;
    }

    @PostMapping("/updateAccident")
    public String update(Model model, @ModelAttribute Accident accident) {
        if (!accidentService.update(accident)) {
            model.addAttribute(USER_ATTRIBUTE, USER_VALUE);
            model.addAttribute(MESSAGE, UNABLE_TO_UPDATE);
            return ERROR_404_PAGE;
        }
        return REDIRECT_INDEX_PAGE;
    }

}
