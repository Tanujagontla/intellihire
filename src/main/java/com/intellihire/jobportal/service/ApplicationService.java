package com.intellihire.jobportal.service;

import com.intellihire.jobportal.model.Application;
import com.intellihire.jobportal.model.ApplicationStatus;
import com.intellihire.jobportal.model.Candidate;
import com.intellihire.jobportal.model.JobPost;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    Application saveApplication(Application application);
    Optional<Application> findById(Long id);
    List<Application> findAllApplications();
    List<Application> findByCandidate(Candidate candidate);
    List<Application> findByJobPost(JobPost jobPost);
    List<Application> findByStatus(ApplicationStatus status);
    List<Application> findByCompanyId(Long companyId);
    void deleteApplication(Long id);
    Application updateApplication(Application application);
    boolean existsByCandidateAndJobPost(Candidate candidate, JobPost jobPost);
    Long countByStatus(ApplicationStatus status);
}
