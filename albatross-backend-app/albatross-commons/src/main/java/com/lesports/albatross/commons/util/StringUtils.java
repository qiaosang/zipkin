package com.lesports.albatross.commons.util;

public final class StringUtils {

    private StringUtils() {
    }

    public static boolean isNotEmpty(String string) {
        return string != null && string.length() > 0;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }
}
