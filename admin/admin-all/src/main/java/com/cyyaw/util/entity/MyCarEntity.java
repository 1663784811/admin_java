package com.cyyaw.util.entity;

import com.cyyaw.store.table.goods.entity.GCart;
import com.cyyaw.store.table.goods.entity.GGoods;
import com.cyyaw.store.table.goods.entity.GStoreGoodsSku;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 我的购物车
 */
@Data
public class MyCarEntity {


    /**
     * 购物车数据
     */
    private GCart gCart;

    /**
     * 商品数据
     */
    private GGoods gGoods;

    /**
     * 已经选择的sku
     */
    @ApiModelProperty(value = "gStoreGoodsSku")
    private GStoreGoodsSku gStoreGoodsSku;

    /**
     * sku 列表
     */
    @ApiModelProperty(value = "gStoreGoodsSkuList")
    private List<GStoreGoodsSku> gStoreGoodsSkuList;

}
