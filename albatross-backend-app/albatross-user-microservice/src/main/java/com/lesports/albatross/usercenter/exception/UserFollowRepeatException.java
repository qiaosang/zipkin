package com.lesports.albatross.usercenter.exception;

/**
 * 用户重复关注异常
 */
public class UserFollowRepeatException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserFollowRepeatException(String message) {
        super(message);
    }
}
