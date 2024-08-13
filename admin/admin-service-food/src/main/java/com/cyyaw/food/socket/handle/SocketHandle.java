package com.cyyaw.food.socket.handle;


import javax.websocket.Session;

public interface SocketHandle {

    String getCode();

    void handle(String msg, Session session);

}
