package com.cyyaw.util.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 计算结果返回
 */
@Data
public class CountGoodsRst {


    @ApiModelProperty(value = "总价", example = "总价")
    BigDecimal allTotalPrice;

    @ApiModelProperty(value = "商品总价", example = "商品总价")
    BigDecimal goodsTotalPrice;

    @ApiModelProperty(value = "商品数量", example = "商品数量")
    BigDecimal goodsNum;

    @ApiModelProperty(value = "快递", example = "快递")
    BigDecimal expressPrice;

    @ApiModelProperty(value = "门店信息", example = "门店信息")
    List<StoreRest> storeRestList;

}
