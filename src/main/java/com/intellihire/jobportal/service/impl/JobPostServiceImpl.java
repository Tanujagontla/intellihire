package com.intellihire.jobportal.service.impl;

import com.intellihire.jobportal.model.*;
import com.intellihire.jobportal.repository.JobPostRepository;
import com.intellihire.jobportal.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobPostServiceImpl implements JobPostService {

    @Autowired
    private JobPostRepository jobPostRepository;

    @Override
    public JobPost saveJobPost(JobPost jobPost) {
        return jobPostRepository.save(jobPost);
    }

    @Override
    public Optional<JobPost> findById(Long id) {
        return jobPostRepository.findById(id);
    }

    @Override
    public List<JobPost> findAllJobPosts() {
        return jobPostRepository.findAll();
    }

    @Override
    public List<JobPost> findByCompany(Company company) {
        return jobPostRepository.findByCompany(company);
    }

    @Override
    public List<JobPost> findByCategory(JobCategory category) {
        return jobPostRepository.findByCategory(category);
    }

    @Override
    public List<JobPost> findByStatus(JobStatus status) {
        return jobPostRepository.findByStatus(status);
    }

    @Override
    public List<JobPost> findByPostedBy(User postedBy) {
        return jobPostRepository.findByPostedBy(postedBy);
    }

    @Override
    public List<JobPost> findActiveJobs() {
        return jobPostRepository.findActiveJobsOrderByCreatedAtDesc(JobStatus.ACTIVE);
    }

    @Override
    public List<JobPost> searchJobs(String title, String location, JobCategory category, JobType jobType) {
        return jobPostRepository.searchJobs(title, location, category, jobType);
    }

    @Override
    public void deleteJobPost(Long id) {
        jobPostRepository.deleteById(id);
    }

    @Override
    public JobPost updateJobPost(JobPost jobPost) {
        return jobPostRepository.save(jobPost);
    }

    @Override
    public Long countActiveJobs() {
        return jobPostRepository.countActiveJobs();
    }
}
