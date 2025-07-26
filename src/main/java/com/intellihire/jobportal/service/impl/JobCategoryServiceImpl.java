package com.intellihire.jobportal.service.impl;

import com.intellihire.jobportal.model.JobCategory;
import com.intellihire.jobportal.repository.JobCategoryRepository;
import com.intellihire.jobportal.service.JobCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobCategoryServiceImpl implements JobCategoryService {

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    @Override
    public JobCategory saveJobCategory(JobCategory jobCategory) {
        return jobCategoryRepository.save(jobCategory);
    }

    @Override
    public Optional<JobCategory> findById(Long id) {
        return jobCategoryRepository.findById(id);
    }

    @Override
    public Optional<JobCategory> findByName(String name) {
        return jobCategoryRepository.findByName(name);
    }

    @Override
    public List<JobCategory> findAllJobCategories() {
        return jobCategoryRepository.findAll();
    }

    @Override
    public void deleteJobCategory(Long id) {
        jobCategoryRepository.deleteById(id);
    }

    @Override
    public JobCategory updateJobCategory(JobCategory jobCategory) {
        return jobCategoryRepository.save(jobCategory);
    }

    @Override
    public boolean existsByName(String name) {
        return jobCategoryRepository.existsByName(name);
    }
}
