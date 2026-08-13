package org.example.quipu.services;

import org.example.quipu.adapters.StorageService;
import org.example.quipu.dto.CreateJobResponse;
import org.example.quipu.models.Job;
import org.example.quipu.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final StorageService storageService;

    public JobService(JobRepository jobRepository, StorageService storageService) {
        this.jobRepository = jobRepository;
        this.storageService = storageService;
    }

    public Optional<Job> getJobById(UUID id) {
        return jobRepository.findById(id);
    }

    public CreateJobResponse createJob() {
        UUID jobId = UUID.randomUUID();
        String s3Key = String.format("uploads/%s.csv", jobId);

        var job = new Job();
        job.setJobId(jobId);
        job.setS3Key(s3Key);
        job.setStatus(Job.JobStatus.PENDING);
        job.setCreatedAt(Instant.now());
        jobRepository.save(job);

        return new CreateJobResponse(jobId, storageService.presignUpload(s3Key));
    }
}
