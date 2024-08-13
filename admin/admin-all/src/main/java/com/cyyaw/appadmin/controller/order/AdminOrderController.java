package com.cyyaw.appadmin.controller.order;


import com.cyyaw.service.OrderService;
import com.cyyaw.store.service.OOrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "订单")
@RestController
@RequestMapping("/appAdmin/{appId}/order")
public class AdminOrderController {

    @Autowired
    private OOrderService gTypeService;

    @Autowired
    private OrderService orderService;

}
