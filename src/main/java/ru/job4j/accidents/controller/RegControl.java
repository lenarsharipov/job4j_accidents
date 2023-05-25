package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.data.AuthorityData;
import ru.job4j.accidents.repository.data.UserData;

@Controller
@AllArgsConstructor
public class RegControl {
    private final PasswordEncoder encoder;
    private final UserData users;
    private final AuthorityData authorities;

    @PostMapping("/register")
    public String regSave(Model model,
                          @ModelAttribute User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        try {
            users.save(user);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (user.getId() == null) {
            var errorMessage = user.getUsername() + " username is in use";
            model.addAttribute("errorMessage", errorMessage);
            return "register";
        }
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

}
