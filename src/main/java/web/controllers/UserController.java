package web.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import web.models.Role;
import web.models.User;
import web.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user")
    public String getUserPage(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("message", "You are logged in as " + principal.getName());
        model.addAttribute("user", user);
        System.out.println(userService.getAllRoles());
        List<Role> roleList = userService.getAllRoles();
        for (Role role: roleList
        ) {
            System.out.println(role);
        }
        return "user/index";
    }
}
