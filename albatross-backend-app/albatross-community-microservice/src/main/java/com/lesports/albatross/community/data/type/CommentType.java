package com.lesports.albatross.community.data.type;

/**
 * @author KILLER
 * @version 2015年10月28日 下午3:27:48
 *          class instructions
 */

public enum CommentType {

    COMMENTS("COMMENTS", 1), //评论
    REPLY("REPLY", 2), //回复
    LIKE("LIKE", 3), //点赞
    UNKNOWN("N/A", 0);
    private final String name;
    private final int code;

    CommentType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static CommentType contentType(String name) {
        switch (name) {

            case "COMMENTS":
                return COMMENTS;
            case "REPLY":
                return REPLY;
            case "LIKE":
                return LIKE;
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
