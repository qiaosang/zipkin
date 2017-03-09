package com.lesports.albatross.community.data.type;

public enum MessageType {
    COMMENT("COMMENT", 0), //评论
    FAVORITE("FAVORITE", 1), //喜欢数
    ALL("ALL", 2), //点赞加评论
    UNKNOWN("N/A", 3);
    private final String name;
    private final int code;

    MessageType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static MessageType contentType(String name) {
        switch (name) {

            case "COMMENT":
                return COMMENT;
            case "FAVORITE":
                return FAVORITE;
            case "ALL":
                return ALL;
            default:
                return UNKNOWN;
        }
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return name;
    }

}
