package com.cyyaw.admin.controller;


import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "排行榜")
@RequestMapping("/admin/{eCode}/sortCharts")
public class SortChartsController {


    @ApiOperation(value = "查询排行榜")
    @GetMapping("/getList")
    public BaseResult getList() {
        // 查询redis 里的 zset
        return BaseResult.ok();
    }

    public void addCharts() {
        // 保存的排行榜前100的人数据
        // 第一步: 查询最后一个人的数据
        // 第二步：如果评分大于最后一个人的则删除最后一个人
        // 第三步：插入当前人评分
    }

}



