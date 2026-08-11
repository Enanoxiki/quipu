package org.example.quipu.dto;

import org.example.quipu.models.Job;
import org.example.quipu.models.JobResult;

import java.time.Instant;
import java.util.UUID;

public record JobResponse(
        UUID jobId,
        Job.JobStatus status,
        Instant createdAt,
        Instant completedAt,
        JobResult result,
        String error
) { }
