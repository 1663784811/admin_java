package com.cyyaw.sql.buildcode.croe.code;

import java.util.Map;

public interface CreateCode {

    /**
     * 输出文件
     *
     * @return
     */
    Boolean out();

    /**
     * 设置数据
     */
    void setDataMap(Map<String, Object> dataMap);

    /**
     * 获取数据
     */
    Map<String, Object> getDataMap();



}
