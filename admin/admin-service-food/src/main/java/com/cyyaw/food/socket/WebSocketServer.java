package com.cyyaw.food.socket;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cyyaw.food.service.FoodBoardService;
import com.cyyaw.food.socket.entity.Board;
import com.cyyaw.food.socket.entity.ConnectSession;
import com.cyyaw.food.socket.handle.SocketHandle;
import com.cyyaw.food.table.entity.FoodBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
@ServerEndpoint("/app/{appId}/food/websocket/{id}/{user}")
public class WebSocketServer {

    private static ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketServer.applicationContext = applicationContext;
    }

    /**
     * 餐桌
     */
    private volatile static ConcurrentHashMap<String, Board> socketBoard = new ConcurrentHashMap<>();
    /**
     * 连接的用户
     */
    private volatile static ConcurrentHashMap<String, ConnectSession> connectSessionMap = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String boardId) throws IOException {
        log.info("============ 餐桌ID: {}", boardId);
        //
        FoodBoardService foodBoardService = applicationContext.getBean(FoodBoardService.class);
        FoodBoard foodBoard = foodBoardService.findByTid(boardId);
        if (null != foodBoard) {
            Board board = socketBoard.get(boardId);
            if (null == board) {
                Board boardTemp = new Board();
                boardTemp.setName("餐桌名");
                socketBoard.put(boardId, boardTemp);
                board = socketBoard.get(boardId);
            }
            // 添加新的Session
            String id = session.getId();
            ConnectSession ct = new ConnectSession();
            ct.setSession(session);
            ct.setBoard(boardId);
            connectSessionMap.put(id, ct);
            CopyOnWriteArrayList<String> userList = board.getUserList();
            userList.add(id);
            // 回复连接信息
            JSONObject json = new JSONObject();
            json.set("code", 1);
            json.set("data", foodBoard);
            sendMessage(JSONUtil.toJsonStr(json), session);
        } else {
            session.close();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        String id = session.getId();
        ConnectSession connectSession = connectSessionMap.get(id);
        if (null != connectSession) {
            String boardId = connectSession.getBoard();
            // 删除餐桌用户
            Board board = socketBoard.get(boardId);
            if (null != board) {
                CopyOnWriteArrayList<String> userList = board.getUserList();
                for (int i = 0; i < userList.size(); i++) {
                    String s = userList.get(i);
                    if (s.equals(id)) {
                        userList.remove(i);
                        i--;
                    }
                }
                if (userList.size() == 0) {
                    socketBoard.remove(boardId);
                }
            }
            // 删除连接用户
            connectSessionMap.remove(id);
            log.info("socket删除餐桌: " + board + " 的用户: " + id);
        }

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @ Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        if (StrUtil.isNotBlank(message)) {
            JSONObject json = new JSONObject(message);
            String code = json.getStr("code");
            if (StrUtil.isNotBlank(code)) {
                Map<String, SocketHandle> socketHandleMap = applicationContext.getBeansOfType(SocketHandle.class);
                for (String key : socketHandleMap.keySet()) {
                    SocketHandle socketHandle = socketHandleMap.get(key);
                    String handleCode = socketHandle.getCode();
                    if (handleCode.equals(code)) {
                        socketHandle.handle(message, session);
                    }
                }
            }
        }
    }

    /**
     * @ Param session
     * @ Param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }


    /**
     * 开始点餐
     */
    public static void crateBoardMessage(String boardId) {
        Board board = socketBoard.get(boardId);
        if (null != board) {
            CopyOnWriteArrayList<String> userList = board.getUserList();
            for (int i = 0; i < userList.size(); i++) {
                ConnectSession connectSession = connectSessionMap.get(userList.get(i));
                Session session = connectSession.getSession();
                sendMessage("{\"code\":1}", session);
            }
        }
    }


    /**
     * 实现服务器主动推送
     */
    public static void sendMessage(String message, Session session) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error(String.valueOf(e));
        }
    }

}
