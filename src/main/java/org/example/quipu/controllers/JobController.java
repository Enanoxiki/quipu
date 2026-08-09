package org.example.quipu.controllers;

import org.example.quipu.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/{jobId}")
    public String jobById(@PathVariable("jobId") String jobId) {
        return jobService.getJobById(jobId);
    }

    @PostMapping()
    public String createJob() {
        return jobService.createJob();
    }
}
