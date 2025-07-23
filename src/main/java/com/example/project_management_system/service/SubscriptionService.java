package com.example.project_management_system.service;

import com.example.project_management_system.model.PlanType;
import com.example.project_management_system.model.Subscription;
import com.example.project_management_system.model.User;

public interface SubscriptionService {

    Subscription createSubscription(User user);

    Subscription getUsersSubscription(Long userId) throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType);

    boolean isVaild(Subscription subscription);
}
