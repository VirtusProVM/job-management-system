package com.jobentry.service;

import com.jobentry.entity.Recruiter;
import com.jobentry.entity.Users;
import com.jobentry.repository.RecruiterRepository;
import com.jobentry.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterService {

    private RecruiterRepository recruiterRepository;
    private UsersRepository usersRepository;

    @Autowired
    public RecruiterService(RecruiterRepository recruiterRepository, UsersRepository usersRepository) {
        this.recruiterRepository = recruiterRepository;
        this.usersRepository = usersRepository;
    }

    public Optional<Recruiter> getOne(Integer id) {
        return recruiterRepository.findById(id);
    }

    public Recruiter addNew(Recruiter recruiter) {
        return recruiterRepository.save(recruiter);
    }


    public Recruiter getCurrentRecruiterProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            Optional<Recruiter> recruiterProfile = getOne(users.getUserID());
            return recruiterProfile.orElse(null);
        } else
            return null;
    }

    public Recruiter getRecruiterByID(int id) {

        return recruiterRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Could not find recruiter with recruiter ID: " + id));
    }

    public void update(Recruiter recruiter) {

        recruiterRepository.save(recruiter);
    }
}
