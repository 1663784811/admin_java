package com.cyyaw.app.controller.friends;


import cn.hutool.json.JSONObject;
import com.cyyaw.service.ContentService;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Api(tags = "APP-内容")
@RequestMapping("/app/{appId}/content")
@RestController
public class ContentController {


    @Autowired
    private ContentService contentService;

    @ApiOperation(value = "查找内容", notes = "查找内容")
    @GetMapping(value = "/findContent")
    public BaseResult findContent(@RequestParam Map<String, Object> map, @PathVariable String appId) {
        JSONObject json = new JSONObject(map);
        Integer page = json.getInt("page");
        Integer size = json.getInt("size");
        return contentService.findContent(appId, page, size);
    }


}
