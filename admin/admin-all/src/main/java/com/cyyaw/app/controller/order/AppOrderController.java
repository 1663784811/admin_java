package com.cyyaw.app.controller.order;


import com.cyyaw.service.OrderService;
import com.cyyaw.store.service.OOrderService;
import com.cyyaw.store.table.order.entity.OOrder;
import com.cyyaw.user.config.TokenData;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.util.entity.CountGoodsRst;
import com.cyyaw.util.entity.SubmitOrder;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "订单")
@RestController
@RequestMapping("/app/{appId}/order")
public class AppOrderController {

    @Autowired
    private OOrderService gTypeService;

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "订单", notes = "订单")
    @GetMapping("/query")
    public BaseResult query(@TokenData LoginInfo loginInfo) {
        String userId = loginInfo.getId();
        return orderService.findMyOrder(userId);
    }

    @ApiOperation(value = "订单详情", notes = "订单详情")
    @GetMapping("/orderById")
    public BaseResult orderById(String orderId) {
        return orderService.orderById(orderId);
    }

    @ApiOperation(value = "删除订单", notes = "删除订单")
    @GetMapping("/del")
    public BaseResult del() {
        return BaseResult.ok();
    }


    @ApiOperation(value = "计算商品价格", notes = "计算商品价格")
    @PostMapping("/countGoodsPrice")
    public BaseResult countGoodsPrice(@RequestBody SubmitOrder submitOrder) {
        CountGoodsRst countGoodsRst = orderService.countGoodsPrice(submitOrder);
        return BaseResult.ok(countGoodsRst);
    }

    @ApiOperation(value = "创建订单", notes = "创建订单")
    @PostMapping("/createOrder")
    public BaseResult createOrder(@RequestBody SubmitOrder submitOrder, @TokenData LoginInfo loginInfo) {
        String userId = loginInfo.getId();
        submitOrder.setUid(userId);
        OOrder order = orderService.createOrder(submitOrder);
        return BaseResult.ok(order);
    }


}
