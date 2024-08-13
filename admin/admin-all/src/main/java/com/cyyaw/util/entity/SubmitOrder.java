package com.cyyaw.util.entity;

import lombok.Data;

import java.util.List;

@Data
public class SubmitOrder {

    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户ID
     */
    private String uid;

    /**
     * 地址ID
     */
    private String addressId;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;

    /**
     * 描述
     */
    private String description;



    /**
     * 商品列表
     */
    private List<CountGoods> goodsList;


}
