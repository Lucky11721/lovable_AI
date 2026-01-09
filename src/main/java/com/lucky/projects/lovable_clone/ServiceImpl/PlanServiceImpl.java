package com.lucky.projects.lovable_clone.ServiceImpl;


import com.lucky.projects.lovable_clone.dto.subscription.PlanResponse;
import com.lucky.projects.lovable_clone.service.PlanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    @Override
    public List<PlanResponse> getAllActivePlans() {
        return List.of();
    }
}
