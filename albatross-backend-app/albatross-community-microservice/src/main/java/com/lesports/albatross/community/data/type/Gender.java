package com.lesports.albatross.community.data.type;

public enum Gender {
    MALE("M"),
    FEMALE("F"),
    NA("NA");

    private final String presentation;

    Gender(String s) {
        presentation = s;
    }

    public static Gender getType(String s) {
        if (s.equalsIgnoreCase("MALE")) {
            return MALE;
        } else if (s.equalsIgnoreCase("FEMALE")) {
            return FEMALE;
        } else {
            return NA;
        }
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : presentation.equals(otherName);
    }

    public String toString() {
        return presentation;
    }
}

