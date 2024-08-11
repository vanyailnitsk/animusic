package com.animusic.s3;

public interface S3Service {

    void putObject(String key, byte[] file, String contentType);

    byte[] getObject(String key);

    void deleteObject(String key);

    String createBlob(String fileName, byte[] content, String contentType);
}
