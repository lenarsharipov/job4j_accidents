package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import org.springframework.ui.Model;

@ThreadSafe
@Controller
@AllArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;
    private static final String MESSAGE = "message";
    private static final String UNABLE_TO_EDIT = "UNABLE TO EDIT SPECIFIED ACCIDENT";
    private static final String ERROR_404_PAGE = "404";
    private static final String EDIT_PAGE = "edit";
    private static final String ACCIDENTS_PAGE = "accidents";
    private static final String CREATE_ACCIDENT_PAGE = "createAccident";
    private static final String REDIRECT_ACCIDENTS_PAGE = "redirect:/accidents";
    private static final String ACCIDENTS_ATTRIBUTE = "accidents";
    private static final String ACCIDENT_ATTRIBUTE = "accident";

    /**
     * List all persisted accidents.
     * @param model model.
     * @return accidents page.
     */
    @GetMapping("/accidents")
    public String showAll(Model model) {
        model.addAttribute(ACCIDENTS_ATTRIBUTE, accidentService.findAll());
        return ACCIDENTS_PAGE;
    }

    /**
     * Get Accident creation page.
     * @return createAccident page.
     */
    @GetMapping("/createAccident")
    public String viewCreateAccident() {
        return CREATE_ACCIDENT_PAGE;
    }

    /**
     * Save new accident.
     * @param accident Accident
     * @return 404 or redirect:/accidents
     */
    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidentService.create(accident);
        return REDIRECT_ACCIDENTS_PAGE;
    }

    /**
     * Get accident edit page by id.
     * @param id id.
     * @param model Model.
     * @return 404 or /edit page.
     */
    @GetMapping("/edit/{id}")
    public String viewUpdateAccident(@PathVariable int id, Model model) {
        var accidentOptional = accidentService.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute(MESSAGE, UNABLE_TO_EDIT);
            return ERROR_404_PAGE;
        }
        model.addAttribute(ACCIDENT_ATTRIBUTE, accidentOptional.get());
        return EDIT_PAGE;
    }

    /**
     * Update found by id accident.
     * @param accident Accident.
     * @return 404 or redirect:/accidents.
     */
    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute Accident accident) {
        var isUpdated = accidentService.update(accident);
        if (!isUpdated) {
            return ERROR_404_PAGE;
        }
        return REDIRECT_ACCIDENTS_PAGE;
    }

}
