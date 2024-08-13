package com.cyyaw.util.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CountGoods {

    /**
     * sku ID
     */
    @ApiModelProperty(value = "skuID", example = "skuID")
    private String skuId;

    @ApiModelProperty(value = "数量", example = "1")
    private Integer number;

}
