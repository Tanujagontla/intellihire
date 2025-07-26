package com.intellihire.jobportal.service;

import com.intellihire.jobportal.model.*;

import java.util.List;
import java.util.Optional;

public interface JobPostService {
    JobPost saveJobPost(JobPost jobPost);
    Optional<JobPost> findById(Long id);
    List<JobPost> findAllJobPosts();
    List<JobPost> findByCompany(Company company);
    List<JobPost> findByCategory(JobCategory category);
    List<JobPost> findByStatus(JobStatus status);
    List<JobPost> findByPostedBy(User postedBy);
    List<JobPost> findActiveJobs();
    List<JobPost> searchJobs(String title, String location, JobCategory category, JobType jobType);
    void deleteJobPost(Long id);
    JobPost updateJobPost(JobPost jobPost);
    Long countActiveJobs();
}
