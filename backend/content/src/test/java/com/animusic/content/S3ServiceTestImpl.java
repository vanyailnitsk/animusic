package com.animusic.content;

import java.util.HashMap;
import java.util.Map;

import com.animusic.s3.S3Service;
import lombok.Getter;

@Getter
public class S3ServiceTestImpl implements S3Service {

    private final Map<String, byte[]> storage = new HashMap<>();

    @Override
    public void putObject(String key, byte[] file, String contentType) {
        storage.put(key, file);
    }

    @Override
    public byte[] getObject(String key) {
        return storage.get(key);
    }

    @Override
    public void deleteObject(String key) {
        storage.remove(key);
    }

    @Override
    public String createBlob(String fileName, byte[] content, String contentType) {
        putObject(fileName, content, contentType);
        return fileName;
    }

}
