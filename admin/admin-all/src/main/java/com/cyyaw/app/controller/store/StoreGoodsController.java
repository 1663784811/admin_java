package com.cyyaw.app.controller.store;

import com.cyyaw.service.ShoppingGoodsService;
import com.cyyaw.store.table.goods.entity.GGoods;
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
@Api(tags = "app-门店-商品")
@RestController
@RequestMapping("/app/{storeId}/store/goods")
public class StoreGoodsController {

    @Autowired
    private ShoppingGoodsService shoppingGoodsService;

    @ApiOperation(value = "门店-商品-分类", notes = "搜索商品")
    @GetMapping("/goodsTypeList")
    public BaseResult goodsTypeList(GGoods gGoods, @PathVariable("storeId") String storeId) {
        gGoods.setStoreId(storeId);
        return shoppingGoodsService.goodsTypeList(gGoods);
    }

}
