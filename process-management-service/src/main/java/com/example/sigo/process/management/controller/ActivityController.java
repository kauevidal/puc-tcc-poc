package com.example.sigo.process.management.controller;

import com.example.sigo.process.management.controller.dto.ActivityDTO;
import com.example.sigo.process.management.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "activity", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public List<ActivityDTO> getAllActivities(@RequestParam long processId) {
        return activityService.findActivitiesByProcess(processId);
    }
}
