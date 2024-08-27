package com.animusic.s3;

import java.util.Objects;

public class StoragePathResolver {

    private static final String ANIME_AUDIO_PATH = "%s/audio/%s";

    private static final String ANIME_IMAGES_PATH = "%s/images/%s";

    private static final String USER_IMAGES_PATH = "users/%d/%s";

    private static String storageUrl;

    private static String publicUrl;

    public static void setStorageUrl(String url, String bucketName) {
        StoragePathResolver.storageUrl = "%s/%s/".formatted(url, bucketName);
    }

    public static void setPublicUrl(String url, String bucketName) {
        StoragePathResolver.publicUrl = "%s/%s/".formatted(url, bucketName);
    }

    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public static String soundtrackAudioFile(
            String animeFolderName,
            String trackName,
            String originalFileName
    ) {
        var extension = Objects.requireNonNull(getFileExtension(originalFileName));
        return ANIME_AUDIO_PATH.formatted(animeFolderName, trackName + extension);
    }

    public static String imageInAnimeFolder(
            String animeFolderName,
            String imageName,
            String originalFileName
    ) {
        var extension = Objects.requireNonNull(getFileExtension(originalFileName));
        return ANIME_IMAGES_PATH.formatted(animeFolderName, imageName + extension);
    }

    public static String imageInUserFolder(
            Integer userId,
            String imageName,
            String originalFileName
    ) {
        var extension = Objects.requireNonNull(getFileExtension(originalFileName));
        return USER_IMAGES_PATH.formatted(userId, imageName + extension);
    }

    public static String getAbsoluteFileUrl(String relativeUrl) {
        return publicUrl + relativeUrl;
    }
}
