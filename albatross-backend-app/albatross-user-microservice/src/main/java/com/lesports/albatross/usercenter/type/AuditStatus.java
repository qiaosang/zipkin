package com.lesports.albatross.usercenter.type;

/**
 * Created by litzuhsien on 9/14/15.
 */

/**
 * @author Administrator
 *         图片审核状态  Y 审核通过 N 等待审核  R 拒绝通过 ,D 用户主动删除  后期如有其它需求  可以扩展
 */
public enum AuditStatus {
    Y, N, R, D;

    public static AuditStatus status(String s) {

        switch (s) {
            case "Y":
                return AuditStatus.Y;
            case "N":
                return AuditStatus.N;
            case "R":
                return AuditStatus.R;
            case "D":
                return AuditStatus.D;
            default:
                return AuditStatus.N;

        }
    }

    @Override
    public String toString() {
        switch (this) {
            case Y:
                return "Y";
            case N:
                return "N";
            case R:
                return "R";
            case D:
                return "D";
            default:
                return "N";
        }
    }
}
