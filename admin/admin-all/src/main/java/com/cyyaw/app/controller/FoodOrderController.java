package com.cyyaw.app.controller;


import cn.hutool.json.JSONObject;
import com.cyyaw.service.CartService;
import com.cyyaw.service.FoodOrderService;
import com.cyyaw.service.OrderService;
import com.cyyaw.store.table.order.entity.OOrder;
import com.cyyaw.util.entity.AddMyCar;
import com.cyyaw.util.entity.SubmitOrder;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Api(tags = "点餐系统")
@RestController
@RequestMapping("/app/{appId}/store/{storeId}/food/board")
public class FoodOrderController {

    @Autowired
    private CartService cartService;
    @Autowired
    private FoodOrderService foodOrderService;

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "开桌", notes = "开桌")
    @PostMapping("/crateBoard/{boardId}")
    public BaseResult crateBoard(@RequestBody Map<String, Object> map, @PathVariable String boardId) {
        JSONObject json = new JSONObject(map);
        Integer number = json.getInt("number");
        if (number != null && number > 0) {
            return foodOrderService.crateBoard(boardId, number);
        } else {
            return BaseResult.fail();
        }
    }

    // 添加或减少菜品,
    @ApiOperation(value = "更新购物车", notes = "更新购物车")
    @PostMapping("/updateCart/{boardId}")
    public BaseResult updateCar(@PathVariable String boardId, @RequestBody AddMyCar addMyCar) {
        BaseResult result = cartService.updateMyCar(boardId, addMyCar);
        // 通知其它手机
        return result;
    }

    @ApiOperation(value = "查询购物车", notes = "查询购物车")
    @GetMapping("/findMyCart/{boardId}")
    public BaseResult findMyCart(@PathVariable String storeId, @PathVariable String boardId) {
        BaseResult result = cartService.myStoreCartList(storeId, boardId);
        return result;
    }


    // 提交菜品
    @ApiOperation(value = "创建订单", notes = "创建订单")
    @PostMapping("/createOrder/{boardId}")
    public BaseResult createOrder(@PathVariable String boardId, @RequestBody SubmitOrder submitOrder) {
        OOrder order = orderService.createFoodOrder(boardId, submitOrder);
        return BaseResult.ok(order);
    }

    // 获取餐桌订单
    @ApiOperation(value = "获取餐桌订单", notes = "获取餐桌订单")
    @GetMapping("/boardOrder/{boardId}")
    public BaseResult boardOrder(@PathVariable String boardId) {
        OOrder order = orderService.boardOrder(boardId);
        return BaseResult.ok(order);
    }


    // 点击支付


}
