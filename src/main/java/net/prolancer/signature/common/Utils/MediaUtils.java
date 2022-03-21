package net.prolancer.signature.common.Utils;

import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class MediaUtils {
    private static Map<String, MediaType> mediaTypeMap;

    static {
        mediaTypeMap = new HashMap<>();
        mediaTypeMap.put("PNG", MediaType.IMAGE_PNG);
        mediaTypeMap.put("PDF", MediaType.APPLICATION_PDF);
    }

    /**
     * Get Media Type
     * @param type
     * @return
     */
    public static MediaType getMediaType(String type) {
        return mediaTypeMap.get(type.toUpperCase());
    }
}
