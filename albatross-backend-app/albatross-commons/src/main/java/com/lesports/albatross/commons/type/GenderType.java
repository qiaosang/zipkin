package com.lesports.albatross.commons.type;

/**
 * Created by Gang Li on 4/25/16.
 * Copyright © 2016 LeSports Inc. All rights reserved.
 */

public enum GenderType {
    MALE("M"),
    FEMALE("F"),
    NA("NA");

    private final String presentation;

    GenderType(String s) {
        presentation = s;
    }

    public static GenderType getType(final String value) {
        switch (value) {
            case "M":
                return MALE;
            case "F":
                return FEMALE;
            default:
                return NA;
        }
    }

    public String toString() {
        return presentation;
    }
}
