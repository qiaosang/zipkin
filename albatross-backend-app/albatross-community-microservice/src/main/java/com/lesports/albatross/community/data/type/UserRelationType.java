package com.lesports.albatross.community.data.type;

/**
 * Created by litzuhsien on 9/14/15.
 * update by wangdong3 on 9/26/16
 */
public enum UserRelationType {
    FOLLOW, FANS, FRIENDS, BLACKLIST, STRANGER;

    public static UserRelationType getRelationType(String s) {
        switch (s) {
            case "FOLLOW":
                return UserRelationType.FOLLOW;
            case "FANS":
                return UserRelationType.FANS;
            case "FRIENDS":
                return UserRelationType.FRIENDS;
            case "BLACKLIST":
                return UserRelationType.BLACKLIST;
            case "STRANGER":
            default:
                return UserRelationType.STRANGER;

        }

    }

    @Override
    public String toString() {
        switch (this) {
            case FOLLOW:
                return "FOLLOW";
            case FANS:
                return "FANS";
            case FRIENDS:
                return "FRIENDS";
            case BLACKLIST:
                return "BLACKLIST";
            case STRANGER:
                return "STRANGERV";
            default:
                return "STRANGER";
        }
    }
}