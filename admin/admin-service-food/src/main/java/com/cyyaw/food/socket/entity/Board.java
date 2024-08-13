package com.cyyaw.food.socket.entity;

import lombok.Data;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 餐桌
 */
@Data
public class Board {

    /**
     * 餐桌名
     */
    private volatile String name;

    /**
     * 当前连接用户ID
     */
    private volatile CopyOnWriteArrayList<String> userList = new CopyOnWriteArrayList();

}
