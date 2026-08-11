package org.example.quipu.config;

import org.example.quipu.models.Job;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Configuration
public class DynamoDbConfig {

    @Bean
    public DynamoDbTable<Job> jobTable(
            DynamoDbEnhancedClient enhancedClient,
            @Value("${quipu.dynamodb.jobs-table}") String tableName
    ) {
        return enhancedClient.table(tableName,
                TableSchema.fromBean(Job.class));
    }
}
