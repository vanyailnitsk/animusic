package com.animusic.s3;

import java.util.Objects;

public class StoragePathResolver {

    private static final String ANIME_AUDIO_FOLDER = "%s/audio/%s";

    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public static String createSoundtrackFileName(
            String animeFolderName,
            String trackName,
            String originalFileName
    ) {
        var extension = Objects.requireNonNull(getFileExtension(originalFileName));
        return ANIME_AUDIO_FOLDER.formatted(animeFolderName,trackName+extension);
    }
}
