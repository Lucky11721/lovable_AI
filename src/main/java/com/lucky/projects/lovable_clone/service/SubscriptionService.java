package com.lucky.projects.lovable_clone.service;


import com.lucky.projects.lovable_clone.Enum.SubscriptionStatus;
import com.lucky.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.lucky.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.lucky.projects.lovable_clone.dto.subscription.PortalResponse;
import com.lucky.projects.lovable_clone.dto.subscription.SubscriptionResponse;

import java.time.Instant;

public interface SubscriptionService {
    SubscriptionResponse getCurrentSubscription();


    PortalResponse openCustomerPortal();

    void activateSubscription(Long userId, Long planId, String subscriptionId, String customerId);

    void updateSubscription(String gatewaySubscriptionId, SubscriptionStatus status, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId);

    void cancelSubscription(String gatewaySubscriptionId);

    void renewSubscriptionPeriod(String subId, Instant periodStart, Instant periodEnd);

    void markSubscriptionPastDue(String subId);

    boolean canCreateNewProject();
}
