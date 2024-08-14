package com.cyyaw.util.entity;


import lombok.Data;

@Data
public class FriendsEntity {
    /**
     * tid
     */
    private String tid;
    /**
     * 房间号
     */
    private String roomId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * appID
     */
    private String appId;

    /**
     * 账号
     */
    private String account;

    /**
     * 性别
     */
    private String sex;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 个性签名
     */
    private String introduceSign;
    /**
     * 头像
     */
    private String face;

    /**
     * 状态
     */
    private Integer status;
}
