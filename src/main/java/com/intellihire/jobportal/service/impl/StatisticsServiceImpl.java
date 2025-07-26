package com.intellihire.jobportal.service.impl;

import com.intellihire.jobportal.model.ApplicationStatus;
import com.intellihire.jobportal.model.Role;
import com.intellihire.jobportal.model.Statistics;
import com.intellihire.jobportal.repository.StatisticsRepository;
import com.intellihire.jobportal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ApplicationService applicationService;

    @Override
    public Statistics getLatestStatistics() {
        List<Statistics> stats = statisticsRepository.findAll();
        if (stats.isEmpty()) {
            return updateStatistics();
        }
        return stats.get(0);
    }

    @Override
    public Statistics updateStatistics() {
        Statistics stats = new Statistics();
        
        // Calculate statistics
        stats.setTotalJobs((long) jobPostService.findAllJobPosts().size());
        stats.setActiveJobs(jobPostService.countActiveJobs());
        stats.setTotalApplications((long) applicationService.findAllApplications().size());
        stats.setTotalCandidates(userService.countByRole(Role.CANDIDATE));
        stats.setTotalCompanies((long) companyService.findAllCompanies().size());
        stats.setPendingApplications(applicationService.countByStatus(ApplicationStatus.PENDING));
        stats.setAcceptedApplications(applicationService.countByStatus(ApplicationStatus.ACCEPTED));
        stats.setRejectedApplications(applicationService.countByStatus(ApplicationStatus.REJECTED));

        // Clear existing statistics and save new ones
        statisticsRepository.deleteAll();
        return statisticsRepository.save(stats);
    }

    @Override
    public void refreshStatistics() {
        updateStatistics();
    }
}
