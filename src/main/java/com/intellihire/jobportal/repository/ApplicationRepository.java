package com.intellihire.jobportal.repository;

import com.intellihire.jobportal.model.Application;
import com.intellihire.jobportal.model.ApplicationStatus;
import com.intellihire.jobportal.model.Candidate;
import com.intellihire.jobportal.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByCandidate(Candidate candidate);
    List<Application> findByJobPost(JobPost jobPost);
    List<Application> findByStatus(ApplicationStatus status);
    
    Optional<Application> findByCandidateAndJobPost(Candidate candidate, JobPost jobPost);
    boolean existsByCandidateAndJobPost(Candidate candidate, JobPost jobPost);
    
    @Query("SELECT COUNT(a) FROM Application a WHERE a.status = :status")
    Long countByStatus(@Param("status") ApplicationStatus status);
    
    @Query("SELECT a FROM Application a WHERE a.jobPost.company.id = :companyId")
    List<Application> findByCompanyId(@Param("companyId") Long companyId);
}
