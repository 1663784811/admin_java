package com.cyyaw.util;

import cn.hutool.json.JSONObject;
import com.cyyaw.user.utils.LoginInfo;

public class SqlCommonUtil {
    private SqlCommonUtil() {
    }


    public static JSONObject setDefaultData(JSONObject json, LoginInfo loginInfo) {
        if (null != json && null != loginInfo) {
            json.put("__user_eCode", loginInfo.getEnterpriseCode());
            json.put("__user_appId", loginInfo.getAppId());
            json.put("__user_uId", loginInfo.getId());
            json.put("__user_account", loginInfo.getAccount());
            json.put("__user_name", loginInfo.getUserName());
        }
        return json;
    }


}
