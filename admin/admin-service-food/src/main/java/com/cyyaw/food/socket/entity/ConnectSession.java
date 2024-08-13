package com.cyyaw.food.socket.entity;

import lombok.Data;

import javax.websocket.Session;

@Data
public class ConnectSession {

    /**
     * 餐桌
     */
    private String board;

    /**
     * 会话
     */
    private Session session;

}
