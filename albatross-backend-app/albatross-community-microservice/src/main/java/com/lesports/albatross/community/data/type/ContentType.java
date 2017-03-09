package com.lesports.albatross.community.data.type;

/**
 * Created by litzuhsien on 9/14/15.
 */
public enum ContentType {
    MOMENT("MOMENT", 1001), // 动态推荐
    USER("USER", 1002), // 用户推荐
    TEACHING("TEACHING", 1003), // 推荐乐学
    VIDEO("VIDEO", 1004), // 推荐视频
    MATCH("MATCH", 1005), // 推荐比赛
    NEWS("NEWS", 1006), // 推荐新闻
    PRIZE("PRIZE", 1007), // 花落谁家
    GAMBLING("GAMBLING", 1008), // 竞猜活动
    LIFESHOW("LIFESHOW", 1009), // 传图活动
    SHORTVIDEO("SHORTVIDEO", 1010), // 短视频
    ACTIVITIES("ACTIVITIES", 1011), // 活动总称
    IMAGESET("IMAGESET", 1012), // 图集
    QUIZZES("QUIZZES", 1013), // 竞猜
    H5TITLE("H5TITLE", 1014),
    HOMEPAGE("HOMEPAGE", 1015),
    NAVIGATION("NAVIGATION", 1016),
    H5SHARE_MATCH("H5SHARE_MATCH", 101701), //h5分享-赛事
    SHARE_MOMENT("SHARE_MOMENT", 1018), //动态分享
    UNKNOWN("N/A", 1000);

    private final String name;
    private final int code;

    ContentType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static ContentType contentType(String name) {
        try {
            return ContentType.valueOf(name);
        } catch (Exception e) {
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
