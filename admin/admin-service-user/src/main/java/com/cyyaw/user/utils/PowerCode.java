package com.cyyaw.user.utils;


/**
 * 菜单
 */
public enum PowerCode {

    shopping("shopping", "商城管理"),
    user("user", "用户管理"),
    finance("finance", "财务管理"),
    store("store", "门店管理"),
    ;
    private String code;
    private String note;

    PowerCode(String code, String note) {
        this.code = code;
        this.note = note;
    }


    public String getCode() {
        return code;
    }

}
