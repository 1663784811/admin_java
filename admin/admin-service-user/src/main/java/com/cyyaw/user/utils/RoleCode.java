package com.cyyaw.user.utils;


/**
 * 角色码表
 */
public enum RoleCode {
    admin("admin", "超级管理员");
    private String code;

    private String note;

    RoleCode(String code, String note) {
        this.code = code;
        this.note = note;
    }


    public String getCode() {
        return code;
    }

}
