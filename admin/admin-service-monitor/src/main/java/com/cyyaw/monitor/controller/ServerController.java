package com.cyyaw.monitor.controller;

import com.cyyaw.monitor.service.ServerInfo;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Api(tags = "服务器监控")
@RequestMapping("/admin/monitor/server")
@RestController
public class ServerController {
    @ApiOperation(value = "获取服务器信息", notes = "获取服务器信息")
    @GetMapping("/serverInfo")
    public BaseResult serverInfo() throws Exception {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.copyTo();
        return BaseResult.ok(serverInfo);
    }

}
