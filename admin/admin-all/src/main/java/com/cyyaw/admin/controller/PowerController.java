package com.cyyaw.admin.controller;

import cn.hutool.json.JSONObject;
import com.cyyaw.user.service.TPowerService;
import com.cyyaw.user.table.entity.TPower;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Api(tags = "菜单管理")
@RequestMapping("/admin/{eCode}/power")
public class PowerController {

    @Autowired
    private TPowerService tPowerService;


    @ApiOperation(value = "查询系统菜单", notes = "查询系统菜单")
    @GetMapping("/queryMenu")
    public BaseResult queryMenu(@RequestParam Map<String, Object> map, @PathVariable String eCode) {
        // 查询数据
        JSONObject json = new JSONObject(map);
        Integer powerType = json.getInt("powerType", 1);
        return tPowerService.queryMenu(eCode, powerType);
    }

    @ApiOperation(value = "保存菜单", notes = "保存菜单")
    @PostMapping("/saveMenu")
    public BaseResult saveMenu(@RequestBody TPower tPower, @PathVariable String eCode) {
        TPower save = tPowerService.save(tPower, eCode);
        return BaseResult.ok(save);
    }

    @ApiOperation(value = "删除菜单", notes = "删除菜单")
    @PostMapping("/delMenu")
    public BaseResult delMenu(@RequestBody TPower tPower) {
        Integer id = tPower.getId();
        tPowerService.delMenu(id);
        return BaseResult.ok();
    }

}
