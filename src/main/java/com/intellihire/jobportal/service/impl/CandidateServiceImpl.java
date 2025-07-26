package com.intellihire.jobportal.service.impl;

import com.intellihire.jobportal.model.Candidate;
import com.intellihire.jobportal.repository.CandidateRepository;
import com.intellihire.jobportal.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public Optional<Candidate> findById(Long id) {
        return candidateRepository.findById(id);
    }

    @Override
    public List<Candidate> findAllCandidates() {
        return candidateRepository.findAll();
    }

    @Override
    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }

    @Override
    public Candidate updateCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }
}
