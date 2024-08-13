package com.cyyaw.tx.phone;


import java.util.List;


public interface PhoneDao {

    /**
     * 获取手机列表
     */
    List<PhoneEntity> phoneList();

    /**
     * 获取截图
     */
    String phoneImage(String phone);

    /**
     * 获取页面结构
     */
    String phoneStructure(String phone);

    /**
     * 获取当前页面信息
     */
    List<String> pageInfo(String phone);

    /**
     * 获取当前页面包名
     */
    String pagePackage(String phone);

    /**
     * 点击主页按钮
     */
    void clickHome(String phoneName);


}
