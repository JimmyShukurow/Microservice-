package io.smartir.smartir.website.utils;

import java.util.HashSet;
import java.util.Set;


public final class FileUtil {

    private FileUtil() {

    }
    private static final Set<String> supportedImageTypes = new HashSet<>();
    static {
        supportedImageTypes.add("jpg");
        supportedImageTypes.add("jpeg");
        supportedImageTypes.add("png");
    }
    public static boolean isSupportedImageType(String pExt) {
        return supportedImageTypes.contains(pExt);
    }

}

