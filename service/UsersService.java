package com.jobentry.service;

import com.jobentry.entity.Candidate;
import com.jobentry.entity.Recruiter;
import com.jobentry.entity.Users;
import com.jobentry.repository.CandidateRepository;
import com.jobentry.repository.RecruiterRepository;
import com.jobentry.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    private RecruiterRepository recruiterRepository;

    private CandidateRepository candidateRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository, RecruiterRepository recruiterRepository,
                        CandidateRepository candidateRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.recruiterRepository = recruiterRepository;
        this.candidateRepository = candidateRepository;
    }

    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Users addNewUser(Users users) {

        users.setActive(true);
        users.setRegisterDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser = usersRepository.save(users);
        int userTypeID = users.getUsersType().getUserTypeID();

        if(userTypeID == 1) {
            recruiterRepository.save(new Recruiter(savedUser));
        } else {
            candidateRepository.save(new Candidate(savedUser));
        }
        return savedUser;
    }

    public Object getCurrentUserProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users users = usersRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Could not found " + "user"));
            int userId = users.getUserID();
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                Recruiter recruiterProfile = recruiterRepository.findById(userId).orElse(new Recruiter());
                return recruiterProfile;
            } else {
                Candidate candidate = candidateRepository.findById(userId).orElse(new Candidate());
                return candidate;
            }
        }

        return null;
    }

    public Users getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));
            return user;
        }

        return null;
    }
}
