package com.cyyaw.service.impl;

import com.cyyaw.food.service.FoodBoardService;
import com.cyyaw.food.socket.WebSocketServer;
import com.cyyaw.food.table.entity.FoodBoard;
import com.cyyaw.service.FoodOrderService;
import com.cyyaw.util.tools.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FoodOrderServiceImpl implements FoodOrderService {


    @Autowired
    private FoodBoardService foodBoardService;

    @Override
    public BaseResult crateBoard(String boardId, Integer number) {
        // 第一步: 查询餐桌状态
        FoodBoard foodBoard = foodBoardService.findByTid(boardId);
        if (null != foodBoard) {
            foodBoard.setStatus(1);
            foodBoard.setNumber(number);
            foodBoardService.save(foodBoard);
            // 第二步: 发送开桌消息到
            WebSocketServer.crateBoardMessage(boardId);
            return BaseResult.ok();
        } else {
            return BaseResult.fail();
        }
    }


}
