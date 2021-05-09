package web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class LoginController {

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

}
