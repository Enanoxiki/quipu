package org.example.quipu.services;

import org.example.quipu.models.Job;
import org.example.quipu.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Optional<Job> getJobById(UUID id) {
        return jobRepository.findById(id);
    }

    public String createJob() {
        UUID jobId = UUID.randomUUID();
        String s3Key = String.format("uploads/%s", jobId);
        var job = new Job();
        job.setJobId(jobId);
        job.setS3Key(s3Key);
        job.setStatus(Job.JobStatus.PENDING);
        job.setCreatedAt(Instant.now());
        return jobRepository.save(job).getJobId().toString();
    }
}
