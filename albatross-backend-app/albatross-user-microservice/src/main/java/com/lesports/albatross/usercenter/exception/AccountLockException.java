package com.lesports.albatross.usercenter.exception;

/**
 * @author wangdong3
 *         用户是否被锁异常
 */
public class AccountLockException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccountLockException(String message) {
        super(message);
    }
}
