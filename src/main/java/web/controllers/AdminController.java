package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.models.Role;
import web.models.User;
import web.services.UserService;
import web.services.UserServiceImpl;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/index";
    }

    @GetMapping("/{id}")
    public String user(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/user";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("role", userService.getAllRoles());
        return "admin/addUser";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @RequestParam(value="role",required = false) String [] role) {
        if (bindingResult.hasErrors()) {
            return "admin/addUser";
        }
        Set<Role> roleSet = new HashSet<>();
        for (String roles : role) {
            roleSet.add(userService.getRoleById(Integer.parseInt(roles)));
        }
        user.setRoles(roleSet);
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("role", userService.getAllRoles());
        return "admin/editUser";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "admin/editUser";
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String removeUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
}
