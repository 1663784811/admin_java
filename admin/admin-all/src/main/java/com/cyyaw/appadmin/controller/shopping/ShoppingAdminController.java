package com.cyyaw.appadmin.controller.shopping;


import com.cyyaw.service.ShoppingGoodsService;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "商城管理")
@RestController
@RequestMapping("/appAdmin/{appId}/shopping/admin")
public class ShoppingAdminController {

    @Autowired
    private ShoppingGoodsService shoppingGoodsService;


    @ApiOperation(value = "查询商品SKU列表", notes = "查询商品SKU列表")
    @GetMapping("/findGoodsSku")
    public BaseResult findGoodsSku(String goodsId){
        return shoppingGoodsService.findGoodsSku(goodsId);
    }



}
