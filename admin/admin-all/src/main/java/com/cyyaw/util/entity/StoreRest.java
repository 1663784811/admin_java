package com.cyyaw.util.entity;

import com.cyyaw.enterprise.table.entity.EStore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class StoreRest {


    @ApiModelProperty(value = "总价", example = "总价")
    private BigDecimal allTotalPrice;

    @ApiModelProperty(value = "商品总价", example = "商品总价")
    private BigDecimal goodsTotalPrice;

    @ApiModelProperty(value = "商品数量", example = "商品数量")
    private BigDecimal goodsNum;

    @ApiModelProperty(value = "快递", example = "快递")
    private BigDecimal expressPrice;

    @ApiModelProperty(value = "门店Id", example = "门店Id")
    private String storeId;

    @ApiModelProperty(value = "门店Id", example = "门店Id")
    private EStore eStore;

    @ApiModelProperty(value = "商品信息", example = "商品信息")
    private List<GoodsRest> goodsRestList;


}
