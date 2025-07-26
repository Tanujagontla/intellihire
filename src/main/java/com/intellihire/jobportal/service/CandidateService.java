package com.intellihire.jobportal.service;

import com.intellihire.jobportal.model.Candidate;

import java.util.List;
import java.util.Optional;

public interface CandidateService {
    Candidate saveCandidate(Candidate candidate);
    Optional<Candidate> findById(Long id);
    List<Candidate> findAllCandidates();
    void deleteCandidate(Long id);
    Candidate updateCandidate(Candidate candidate);
}
