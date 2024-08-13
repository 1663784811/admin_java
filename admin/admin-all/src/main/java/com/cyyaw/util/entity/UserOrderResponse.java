package com.cyyaw.util.entity;


import com.cyyaw.enterprise.table.entity.EStore;
import com.cyyaw.store.table.order.entity.ODetails;
import com.cyyaw.store.table.order.entity.OOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserOrderResponse {


    @ApiModelProperty(value = "订单")
    private OOrder order;

    @ApiModelProperty(value = "订单详情")
    private List<ODetails> detailsList;

    @ApiModelProperty()
    private EStore store;

}
