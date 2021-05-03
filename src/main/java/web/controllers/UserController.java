package web.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import web.models.User;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserDAOImpl userDAO = new UserDAOImpl();

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userDAO.getAllUsers());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String user(@PathVariable int id, Model model) {
        model.addAttribute("user", userDAO.getUserById(id));
        return "users/user";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/addUser";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/addUser";
        }
        userDAO.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userDAO.getUserById(id));
        return "users/editUser";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "users/editUser";
        }
        userDAO.updateUser(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String removeUser(@PathVariable("id") int id) {
        userDAO.removeUser(id);
        return "redirect:/users";
    }

}
