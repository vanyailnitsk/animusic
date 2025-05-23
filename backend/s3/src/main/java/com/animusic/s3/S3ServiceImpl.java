package com.animusic.s3;

import java.io.IOException;
import java.util.Objects;

import com.animusic.common.S3Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service{

    private final S3Client s3Client;

    private final S3Properties s3Properties;

    @Override
    public void putObject(String key, byte[] file, String contentType) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(s3Properties.getBucket())
                .key(key)
                .contentType(contentType)
                .build();
        s3Client.putObject(objectRequest, RequestBody.fromBytes(file));
    }

    @Override
    public byte[] getObject(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(s3Properties.getBucket())
                .key(key)
                .build();
        ResponseInputStream<GetObjectResponse> object = s3Client.getObject(getObjectRequest);
        try {
            return object.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteObject(String key) {
        if (!Objects.isNull(key)) {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(s3Properties.getBucket())
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        }
    }

    @Override
    public String createBlob(String fileName, byte[] content, String contentType) {
        putObject(fileName, content, contentType);
        return fileName;
    }

}
