package org.example.quipu.services;

import org.springframework.stereotype.Service;

@Service
public class JobService {

    public String getJobById(String id) {
        return String.format("JobService.getJobById: %s", id);
    }

    public String createJob() {
        return "JobService.createJob";
    }
}
