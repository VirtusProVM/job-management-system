package com.jobentry.controller;

import com.jobentry.entity.Recruiter;
import com.jobentry.entity.Users;
import com.jobentry.repository.UsersRepository;
import com.jobentry.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/recruiter")
public class RecruiterController {

    private UsersRepository usersRepository;
    private RecruiterService recruiterService;

    @Autowired
    public RecruiterController(UsersRepository usersRepository, RecruiterService recruiterService) {
        this.usersRepository = usersRepository;
        this.recruiterService = recruiterService;
    }

    @GetMapping("/")
    public String recruiterProfilePage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("Could not " + "found user"));
            Optional<Recruiter> recruiter = recruiterService.getOne(users.getUserID());

            if (!recruiter.isEmpty())
                model.addAttribute("profile", recruiter.get());

        }

        return "edit-recruiter-profile";
    }

    @PostMapping("/addNew")
    public String addNew(Recruiter recruiter, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("Could not " + "found user"));
            recruiter.setUsersID(users);
            recruiter.setRecruiterID(users.getUserID());
        }
        model.addAttribute("profile", recruiter);
        Recruiter savedUser = recruiterService.addNew(recruiter);

        return "redirect:/dashboard/";
    }

    @GetMapping("/profile")
    public String profile(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("Could not " + "found user"));
            Optional<Recruiter> recruiter = recruiterService.getOne(users.getUserID());

            if (!recruiter.isEmpty())
                model.addAttribute("recruiter", recruiter.get());

        }


        return "recruiter-profile";
    }

}
