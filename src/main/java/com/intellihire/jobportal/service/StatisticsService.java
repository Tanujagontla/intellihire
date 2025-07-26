package com.intellihire.jobportal.service;

import com.intellihire.jobportal.model.Statistics;

public interface StatisticsService {
    Statistics getLatestStatistics();
    Statistics updateStatistics();
    void refreshStatistics();
}
