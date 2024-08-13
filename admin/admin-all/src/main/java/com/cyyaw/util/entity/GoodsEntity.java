package com.cyyaw.util.entity;

import com.cyyaw.enterprise.table.entity.EStore;
import com.cyyaw.store.table.goods.entity.GGoods;
import com.cyyaw.store.table.goods.entity.GGoodsSearch;
import com.cyyaw.store.table.goods.entity.GStoreGoodsSku;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class GoodsEntity {


    @ApiModelProperty(value = "商品")
    private GGoods gGoods;

    @ApiModelProperty(value = "商品")
    private GGoodsSearch goodsSearch;

    @ApiModelProperty(value = "已经选择的sku")
    private GStoreGoodsSku gStoreGoodsSku;

    @ApiModelProperty(value = "sku 列表")
    private List<GStoreGoodsSku> gStoreGoodsSkuList;

    @ApiModelProperty(value = "门店")
    private EStore eStore;


    @ApiModelProperty(value = "")
    private Map<String, Set> skuAttr;

}
