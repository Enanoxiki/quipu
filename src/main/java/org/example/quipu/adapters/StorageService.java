package org.example.quipu.adapters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Service
public class StorageService {
    private final S3Presigner s3Presigner;
    private final String bucket;
    private final Duration urlTTL;

    public StorageService(S3Presigner s3Presigner,
                          @Value("${quipu.s3.bucket}") String bucket,
                          @Value("${quipu.s3.upload-url-ttl}") Duration urlTTL) {
        this.s3Presigner = s3Presigner;
        this.bucket = bucket;
        this.urlTTL = urlTTL;
    }

    public String presignUpload(String key) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType("text/csv")
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(urlTTL)
                .putObjectRequest(objectRequest)
                .build();

        return s3Presigner.presignPutObject(presignRequest).url().toString();
    }
}
