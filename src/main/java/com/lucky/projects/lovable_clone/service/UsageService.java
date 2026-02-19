package com.lucky.projects.lovable_clone.service;

import com.lucky.projects.lovable_clone.dto.subscription.PlanLimitsResponse;
import com.lucky.projects.lovable_clone.dto.subscription.UsageTodayResponse;

public interface UsageService {
     UsageTodayResponse getTodayUsageOfUser(Long userId);

    PlanLimitsResponse getCurrentSubscriptionLimitsOfUser(Long userId);
}
