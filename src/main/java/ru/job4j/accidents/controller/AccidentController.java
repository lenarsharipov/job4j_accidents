package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ThreadSafe
@Controller
@AllArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;
    private static final String MESSAGE = "message";
    private static final String UNABLE_TO_UPDATE = "UNABLE TO UPDATE SPECIFIED ACCIDENT";
    private static final String UNABLE_TO_FIND_BY_ID = "UNABLE TO FIND ACCIDENT BY SPECIFIED ID";
    private static final String ERROR_404_PAGE = "404";
    private static final String REDIRECT_INDEX_PAGE = "redirect:/index";
    private static final String CREATE_ACCIDENT_PAGE = "createAccident";
    private static final String UPDATE_ACCIDENT_PAGE = "updateAccident";
    private static final String ACCIDENT = "accident";
    private static final String USER = "user";
    private static final String TYPES = "types";
    private static final String RULES = "rules";
    private static final String R_IDS = "rIds";
    private static final String SELECTED_TYPE_ID = "selectedTypeId";
    private static final String SELECTED_R_IDS = "selectedRIds";

    /**
     * Get Accident creation page.
     * @return createAccident page.
     */
    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute(RULES, ruleService.findAll());
        model.addAttribute(TYPES, accidentTypeService.findAll());
        var user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute(USER, user);
        return CREATE_ACCIDENT_PAGE;
    }

    /**
     * Save new accident.
     * @param accident Accident.
     * @return 404 or redirect:/accidents.
     */
    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest request) {
        var rIds = request.getParameterValues(R_IDS);
        accidentService.save(accident, rIds);
        return REDIRECT_INDEX_PAGE;
    }

    private List<Integer> getRIds(Set<Rule> rules) {
        return rules.stream()
                .map(Rule::getId)
                .collect(Collectors.toList());
    }

    @GetMapping("/formUpdateAccident")
    public String viewUpdate(@RequestParam("id") int id, Model model) {
        var accidentOptional = accidentService.findById(id);
        var user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute(USER, user);
        if (accidentOptional.isEmpty()) {
            model.addAttribute(MESSAGE, UNABLE_TO_FIND_BY_ID);
            return ERROR_404_PAGE;
        }
        var accident = accidentOptional.get();
        var selectedIds = getRIds(accident.getRules());
        model.addAttribute(SELECTED_R_IDS, selectedIds);
        model.addAttribute(RULES, ruleService.findAll());
        model.addAttribute(TYPES, accidentTypeService.findAll());
        model.addAttribute(SELECTED_TYPE_ID, accident.getType().getId());
        model.addAttribute(ACCIDENT, accident);
        return UPDATE_ACCIDENT_PAGE;
    }

    @PostMapping("/updateAccident")
    public String update(Model model,
                         @ModelAttribute Accident accident,
                         HttpServletRequest request) {
        var rIds = request.getParameterValues(R_IDS);
        var user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute(USER, user);
        if (!accidentService.update(accident, rIds)) {
            model.addAttribute(MESSAGE, UNABLE_TO_UPDATE);
            return ERROR_404_PAGE;
        }
        return REDIRECT_INDEX_PAGE;
    }

}
