package com.cyyaw.admin.controller;


import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "地理位置")
@RequestMapping("/admin/{eCode}/geographic")
public class GeographicController {


    @ApiOperation(value = "查询地理位置内的点")
    @GetMapping("/findGeo")
    public BaseResult findRange() {
        // 查询redis 里的 Geographic



        return BaseResult.ok();
    }


    /**
     * 保存地理位置
     */
    public void saveGeographic() {



    }

}



