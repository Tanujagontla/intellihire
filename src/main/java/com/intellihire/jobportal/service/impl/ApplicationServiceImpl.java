package com.intellihire.jobportal.service.impl;

import com.intellihire.jobportal.model.Application;
import com.intellihire.jobportal.model.ApplicationStatus;
import com.intellihire.jobportal.model.Candidate;
import com.intellihire.jobportal.model.JobPost;
import com.intellihire.jobportal.repository.ApplicationRepository;
import com.intellihire.jobportal.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import Transactional

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    @Transactional // For write operations
    public Application saveApplication(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    @Transactional(readOnly = true) // For read operations
    public Optional<Application> findById(Long id) {
        Optional<Application> application = applicationRepository.findById(id);
        application.ifPresent(app -> {
            // Initialize lazy loaded associations
            app.getCandidate().getFirstName();
            app.getJobPost().getTitle();
            app.getJobPost().getCompany().getName();
        });
        return application;
    }

    @Override
    @Transactional(readOnly = true) // For read operations
    public List<Application> findAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        // Initialize lazy loaded associations for each application
        applications.forEach(app -> {
            app.getCandidate().getFirstName(); // Access candidate's first name to initialize
            app.getJobPost().getTitle(); // Access job post title to initialize
            app.getJobPost().getCompany().getName(); // Access company name to initialize
        });
        return applications;
    }

    @Override
    @Transactional(readOnly = true) // For read operations
    public List<Application> findByCandidate(Candidate candidate) {
        List<Application> applications = applicationRepository.findByCandidate(candidate);
        // Initialize lazy loaded associations for each application
        applications.forEach(app -> {
            app.getJobPost().getTitle(); // Access job post title to initialize
            app.getJobPost().getCompany().getName(); // Access company name to initialize
        });
        return applications;
    }

    @Override
    @Transactional(readOnly = true) // For read operations
    public List<Application> findByJobPost(JobPost jobPost) {
        List<Application> applications = applicationRepository.findByJobPost(jobPost);
        // Initialize lazy loaded associations for each application
        applications.forEach(app -> {
            app.getCandidate().getFirstName(); // Access candidate's first name to initialize
        });
        return applications;
    }

    @Override
    @Transactional(readOnly = true) // For read operations
    public List<Application> findByStatus(ApplicationStatus status) {
        List<Application> applications = applicationRepository.findByStatus(status);
        // Initialize lazy loaded associations for each application
        applications.forEach(app -> {
            app.getCandidate().getFirstName();
            app.getJobPost().getTitle();
            app.getJobPost().getCompany().getName();
        });
        return applications;
    }

    @Override
    @Transactional(readOnly = true) // For read operations
    public List<Application> findByCompanyId(Long companyId) {
        List<Application> applications = applicationRepository.findByCompanyId(companyId);
        // Initialize lazy loaded associations for each application
        applications.forEach(app -> {
            app.getCandidate().getFirstName();
            app.getJobPost().getTitle();
            app.getJobPost().getCompany().getName();
        });
        return applications;
    }

    @Override
    @Transactional // For write operations
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }

    @Override
    @Transactional // For write operations
    public Application updateApplication(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    @Transactional(readOnly = true) // For read operations
    public boolean existsByCandidateAndJobPost(Candidate candidate, JobPost jobPost) {
        return applicationRepository.existsByCandidateAndJobPost(candidate, jobPost);
    }

    @Override
    @Transactional(readOnly = true) // For read operations
    public Long countByStatus(ApplicationStatus status) {
        return applicationRepository.countByStatus(status);
    }
}
