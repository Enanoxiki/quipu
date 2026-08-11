package org.example.quipu.dto;

import java.util.UUID;

public record CreateJobResponse(UUID jobId, String uploadUrl) { }
