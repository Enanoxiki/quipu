package org.example.quipu.controllers;

import org.example.quipu.dto.CreateJobResponse;
import org.example.quipu.models.Job;
import org.example.quipu.services.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<Job> jobById(@PathVariable("jobId") UUID jobId) {
        return jobService.getJobById(jobId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CreateJobResponse> createJob() {
        CreateJobResponse job = jobService.createJob();
        return ResponseEntity.created(URI.create("/api/jobs/" + job.jobId())).body(job);
    }
}
