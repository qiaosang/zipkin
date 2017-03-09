package com.lesports.albatross.usercenter.type;

/**
 * Created by qinfeng on 17/1/16.
 * 勋章类型
 */

public enum MedalType {

    WATCHVIDEO(1), COMMUNITY(2), STARTUP(3), QUIZ(4);

    private int code;

    MedalType(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
