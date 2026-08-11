package org.example.quipu.repositories;

import org.example.quipu.models.Job;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.IgnoreNullsMode;
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JobRepository {
    private final DynamoDbTable<Job> jobTable;

    public JobRepository(DynamoDbTable<Job> jobTable) {
        this.jobTable = jobTable;
    }

    public Job save(Job job) {
        jobTable.putItem(job);
        return job;
    }

    public Optional<Job> findById(UUID jobID) {
        Key key = Key.builder().partitionValue(jobID.toString()).build();
        return Optional.ofNullable(jobTable.getItem(key));
    }

    public Job update(Job job) {
        return jobTable.updateItem(UpdateItemEnhancedRequest.builder(Job.class)
                .item(job)
                .ignoreNullsMode(IgnoreNullsMode.MAPS_ONLY)
                .build());
    }
}
