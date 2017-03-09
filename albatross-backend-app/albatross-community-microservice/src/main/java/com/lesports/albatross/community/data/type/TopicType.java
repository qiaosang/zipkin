package com.lesports.albatross.community.data.type;

/**
 * Created by qinfeng1 on 2016/8/12.
 */
public enum TopicType {
    SYSTEM("SYSTEM", 0), // 系统标签
    CUSTOM("CUSTOM", 1); // 自定义标签

    private final String name;
    private final int code;

    TopicType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    TopicType tagType(String name) {
        return TopicType.valueOf(name);
    }

    public int getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return this.name;
    }


}
