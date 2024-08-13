package com.cyyaw.util.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class AddMyCar {

    @ApiModelProperty(value = "数量", example = "1")
    private Integer number;

    @ApiModelProperty(value = "skuId", example = "abc")
    private String skuId;

}
