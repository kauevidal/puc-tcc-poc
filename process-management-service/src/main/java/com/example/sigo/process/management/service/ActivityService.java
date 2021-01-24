package com.example.sigo.process.management.service;

import com.example.sigo.process.management.client.MockAPIClient;
import com.example.sigo.process.management.controller.dto.ActivityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private MockAPIClient client;

    public List<ActivityDTO> findActivitiesByProcess(long processId) {
        return client.getActivityByProcess(processId);
    }
}
