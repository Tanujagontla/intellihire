package com.intellihire.jobportal.service;

import com.intellihire.jobportal.model.JobCategory;

import java.util.List;
import java.util.Optional;

public interface JobCategoryService {
    JobCategory saveJobCategory(JobCategory jobCategory);
    Optional<JobCategory> findById(Long id);
    Optional<JobCategory> findByName(String name);
    List<JobCategory> findAllJobCategories();
    void deleteJobCategory(Long id);
    JobCategory updateJobCategory(JobCategory jobCategory);
    boolean existsByName(String name);
}
