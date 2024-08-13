package com.cyyaw.util.entity;
import com.cyyaw.store.table.goods.entity.GGoods;
import com.cyyaw.store.table.goods.entity.GStoreGoodsSku;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 计算商品信息
 */
@Data
public class GoodsRest {

    /**
     * 商品信息
     */
    private GGoods gGoods;

    /**
     * sku 信息
     */
    private GStoreGoodsSku gStoreGoodsSku;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 单价格
     */
    private BigDecimal price;
    /**
     * 总价
     */
    private BigDecimal totalPrice;
}
