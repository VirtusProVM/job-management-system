package com.jobentry.service;

import com.jobentry.entity.Candidate;
import com.jobentry.entity.Users;
import com.jobentry.repository.CandidateRepository;
import com.jobentry.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
public class CandidateService {

    private CandidateRepository candidateRepository;

    private UsersRepository usersRepository;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository, UsersRepository usersRepository) {
        this.candidateRepository = candidateRepository;
        this.usersRepository = usersRepository;
    }

    public Optional<Candidate> getOne(Integer id) {
        return candidateRepository.findById(id);
    }

    public Candidate addNew(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public Candidate getCurrentCandidateProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            Optional<Candidate> candidate = getOne(users.getUserID());
            return candidate.orElse(null);
        } else return null;

    }

    public Candidate getCandidateByID(int id) {
        return candidateRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Could not find candidate with candidate ID: " + id));
    }

    public void update(Candidate candidate) {
        candidateRepository.save(candidate);
    }

    public Candidate getImagePath(int id) {

        Optional<Candidate> optional = candidateRepository.findById(id);
        Candidate candidate = optional.get();
        String path = candidate.getPhotosImagePath();
        System.out.println("Image path: " + path);

        return candidate;
    }

}
