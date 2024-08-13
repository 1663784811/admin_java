package com.cyyaw.util.entity;

import com.cyyaw.enterprise.table.entity.EStore;
import com.cyyaw.store.table.goods.entity.GCart;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CartListResponse {

    @ApiModelProperty(value = "门店", example = "eStore")
    private EStore eStore;


    @ApiModelProperty(value = "cartList", example = "cartList")
    private List<GCart>  cartList;


}
