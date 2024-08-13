package com.cyyaw.food.socket.handle;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

/**
 * 心跳处理
 */
@Slf4j
@Component
public class ConnectHeart implements SocketHandle {

    @Override
    public String getCode() {
        return "0";
    }

    @Override
    public void handle(String msg, Session session) {
        log.info("========= 收到心跳: {}", msg);
    }
}
