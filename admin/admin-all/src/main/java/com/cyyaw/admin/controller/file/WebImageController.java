package com.cyyaw.admin.controller.file;

import cn.hutool.json.JSONObject;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.PageRespone;
import com.cyyaw.web.service.WebImageService;
import com.cyyaw.web.table.entity.WebImage;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Slf4j
@Api(tags = "影像管理")
@RequestMapping("/admin/{eCode}/image")
@RestController
public class WebImageController {

    @Autowired
    private WebImageService webImageService;

    /**
     * 分页条件查询
     */
    @GetMapping("/findPageWebImage")
    public BaseResult<WebImage> findPageWebImage(@RequestParam Map<String, Object> map) {
        PageRespone<WebImage> page = webImageService.findPage(new JSONObject(map));
        return BaseResult.ok(page);
    }



}
