package com.cyyaw.app.controller.shopping;


import cn.hutool.json.JSONObject;
import com.cyyaw.store.service.GTypeService;
import com.cyyaw.store.table.goods.entity.GType;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = "app-商品品类")
@RestController
@RequestMapping("/app/{appId}/shopping/type")
public class ShoppingTypeController {

    @Autowired
    private GTypeService gTypeService;

    @ApiOperation(value = "商品品类", notes = "商品品类")
    @GetMapping("/findType")
    public BaseResult enterpriseType(GType gType) {
        JSONObject object = new JSONObject();
        List<GType> data = gTypeService.findTree(object);
        return BaseResult.ok(data);
    }


}
