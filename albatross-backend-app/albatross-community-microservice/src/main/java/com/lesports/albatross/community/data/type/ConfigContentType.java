package com.lesports.albatross.community.data.type;

public enum ConfigContentType {

    MOMENT("MOMENT"), SORT("SORT"), CONTROL("CONTROL"), UNKNOWN("UNKNOWN");
    private final String name;

    private ConfigContentType(String name) {
        this.name = name;
    }

    public static ConfigContentType configType(String name) {
        switch (name) {
            case "SORT":
                return SORT;
            default:
                return UNKNOWN;
        }

    }

    @Override
    public String toString() {
        return name;
    }
}
