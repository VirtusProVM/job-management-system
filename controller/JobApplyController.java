package com.jobentry.controller;

import com.jobentry.entity.*;
import com.jobentry.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class JobApplyController {

    private UsersService usersService;

    private JobsService jobsService;

    private RecruiterService recruiterService;

    private CandidateService candidateService;

    private CandidateApplyService candidateApplyService;

    private CandidateSaveService candidateSaveService;

    @Autowired
    public JobApplyController(UsersService usersService, JobsService jobsService, RecruiterService recruiterService,
                              CandidateService candidateService, CandidateApplyService candidateApplyService,
                              CandidateSaveService candidateSaveService) {
        this.usersService = usersService;
        this.jobsService = jobsService;
        this.recruiterService = recruiterService;
        this.candidateService = candidateService;
        this.candidateApplyService = candidateApplyService;
        this.candidateSaveService = candidateSaveService;
    }



    @PostMapping("job-details/apply/{id}")
    public String apply(@PathVariable("id") int id, CandidateApply candidateApply) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersService.getUserByEmail(currentUsername);
            Optional<Candidate> candidate = candidateService.getOne(user.getUserID());
            Jobs job = jobsService.getJob(id);
            if (candidate.isPresent() && job != null) {
                candidateApply = new CandidateApply();
                candidateApply.setCandidate(candidate.get());
                candidateApply.setJob(job);
                candidateApply.setDate(new Date());
                candidateApply.setCoverLetter(candidate.get().getResume());
            } else {
                throw new RuntimeException("User not found");
            }
            candidateApplyService.addNew(candidateApply);
        }

        return "redirect:/";
    }

}
