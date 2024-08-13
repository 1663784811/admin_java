package com.cyyaw.util.entity;


import com.cyyaw.store.table.order.entity.ODetails;
import com.cyyaw.store.table.order.entity.OOrder;
import lombok.Data;

import java.util.List;

@Data
public class SubmitOrderRest {

    /**
     * 订单
     */
    private OOrder order;

    /**
     * 订单详情
     */
    private List<ODetails> oDetailsList;
}
