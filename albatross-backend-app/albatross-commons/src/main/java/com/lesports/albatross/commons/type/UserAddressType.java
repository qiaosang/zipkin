package com.lesports.albatross.commons.type;


public enum UserAddressType {
    MALL, PERSONAL, NA;

    public static UserAddressType getType(String s) {
        switch (s) {
            case "MALL":
                return MALL;
            case "PERSONAL":
                return PERSONAL;
            default:
                return NA;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case MALL:
                return "MALL";
            case PERSONAL:
                return "PERSONAL";
            default:
                return "NA";
        }
    }


}