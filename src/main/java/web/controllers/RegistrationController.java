package web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.models.Role;
import web.models.User;
import web.services.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String newUser(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping
    public String registration(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(userService.getRoleById(2));
        user.setRoles(roleSet);
        userService.addUser(user);
        return "redirect:/login";
    }

}
