package com.lesports.albatross.community.data.type;

/**
 * Created by qinfeng1 on 2016/9/28.
 * 动态查询条件
 */
public enum MomentQueryType {

    NEWEST("NEWEST", 0),    //最新
    FOLLOW("FOLLOW", 1),    //关注
    TOPIC("TOPIC", 2),      //话题
    UNKNOWN("N/A", 3);
    private final String name;
    private final int code;

    MomentQueryType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static MomentQueryType queryType(String name) {
        switch (name) {
            case "NEWEST":
                return NEWEST;
            case "FOLLOW":
                return FOLLOW;
            case "TOPIC":
                return TOPIC;
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
