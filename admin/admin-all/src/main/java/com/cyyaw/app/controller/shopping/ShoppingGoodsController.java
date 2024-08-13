package com.cyyaw.app.controller.shopping;

import com.cyyaw.service.ShoppingGoodsService;
import com.cyyaw.store.table.goods.entity.GGoodsSearch;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "app-商品")
@RestController
@RequestMapping("/app/{appId}/shopping/goods")
public class ShoppingGoodsController {

    @Autowired
    private ShoppingGoodsService shoppingGoodsService;

    @ApiOperation(value = "搜索商品", notes = "搜索商品")
    @GetMapping("/searchGoods")
    public BaseResult searchGoods(GGoodsSearch goodsSearch, @PathVariable("appId") String appId) {
        return shoppingGoodsService.searchGoods(goodsSearch);
    }


    @ApiOperation(value = "商品详情", notes = "商品详情")
    @GetMapping("/goodsDetails")
    public BaseResult goodsDetails(String skuId) {
        return shoppingGoodsService.goodsDetails(skuId);
    }

    @ApiOperation(value = "商品文字详情", notes = "商品文字详情")
    @GetMapping("/goodsDetailsText")
    public BaseResult goodsDetailsText(String goodsId) {
        return shoppingGoodsService.goodsDetailsText(goodsId);
    }

    @ApiOperation(value = "商品图片", notes = "商品图片")
    @GetMapping("/goodsPhoto")
    public BaseResult goodsPhoto(String goodsId) {
        return shoppingGoodsService.goodsPhoto(goodsId);
    }

}
