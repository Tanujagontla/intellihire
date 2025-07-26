package com.intellihire.jobportal.repository;

import com.intellihire.jobportal.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    List<JobPost> findByCompany(Company company);
    List<JobPost> findByCategory(JobCategory category);
    List<JobPost> findByStatus(JobStatus status);
    List<JobPost> findByPostedBy(User postedBy);
    
    @Query("SELECT j FROM JobPost j WHERE j.status = :status ORDER BY j.createdAt DESC")
    List<JobPost> findActiveJobsOrderByCreatedAtDesc(@Param("status") JobStatus status);
    
    @Query("SELECT j FROM JobPost j WHERE " +
           "(:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
           "(:category IS NULL OR j.category = :category) AND " +
           "(:jobType IS NULL OR j.jobType = :jobType) AND " +
           "j.status = 'ACTIVE'")
    List<JobPost> searchJobs(@Param("title") String title, 
                            @Param("location") String location, 
                            @Param("category") JobCategory category, 
                            @Param("jobType") JobType jobType);
    
    @Query("SELECT COUNT(j) FROM JobPost j WHERE j.status = 'ACTIVE'")
    Long countActiveJobs();
}
