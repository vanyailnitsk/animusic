package com.ilnitsk.animusic.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final S3Config s3Config;

    public void putObject(String key,byte[] file) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(s3Config.getBucket())
                .key(key)
                .build();
        s3Client.putObject(objectRequest, RequestBody.fromBytes(file));
    }

    public byte[] getObject(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(s3Config.getBucket())
                .key(key)
                .build();
        ResponseInputStream<GetObjectResponse> object = s3Client.getObject(getObjectRequest);
        try {
            return object.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
