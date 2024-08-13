package com.cyyaw.service.em;

import lombok.Getter;

@Getter
public enum LoginType {

    root(0,"超级管理员")
    ,enterAdmin(1, "企业管理员")
    ,appAdmin(2, "APP管理员")
    ,appUser(3, "APP用户")
    ;
    private final Integer type;
    private final String note;

    LoginType(Integer type, String note) {
        this.type = type;
        this.note = note;
    }

}
