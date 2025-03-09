package com.jobentry.controller;

import com.jobentry.entity.Users;
import com.jobentry.entity.UsersType;
import com.jobentry.service.UsersService;
import com.jobentry.service.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    private UsersTypeService usersTypeService;
    private UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService, UsersTypeService usersTypeService){
        this.usersService = usersService;
        this.usersTypeService = usersTypeService;
    }


    @GetMapping("/register")
    public String register(Model model) {
        List<UsersType> types = usersTypeService.getAll();

        model.addAttribute("types", types);
        model.addAttribute("user", new Users());

        return "register";
    }

    @PostMapping("/register/new")
    public String registerUser(@Valid Users users, Model model) {

        Optional<Users> op = usersService.findByEmail(users.getEmail());

        if(op.isPresent()) {
            model.addAttribute("error", "Email already registered,try to login or register with other email.");
            List<UsersType> types = usersTypeService.getAll();

            model.addAttribute("types", types);
            model.addAttribute("user", new Users());

            return "register";
        }

        usersService.addNewUser(users);

        return "redirect:/dashboard/";
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }
}
