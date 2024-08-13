package com.cyyaw.jpa;

import java.util.HashMap;
import java.util.Map;

public final class BaseConstants {

    //=====
    public static Map tableDelSuccess;

    static {
        tableDelSuccess = new HashMap<String, Object>();
        tableDelSuccess.put("message", "删除成功");
        tableDelSuccess.put("success", true);
    }

    public static Map statusMessage(Boolean status,String message){
        HashMap<String, Object> loginMap = new HashMap<>();
        loginMap.put("success", status);
        loginMap.put("message", message);
        return loginMap;
    }

}
