package com.cyyaw.admin.controller;


import cn.hutool.json.JSONObject;
import com.cyyaw.service.RoleService;
import com.cyyaw.user.service.TDepartmentService;
import com.cyyaw.user.service.TRoleService;
import com.cyyaw.user.table.entity.TDepartment;
import com.cyyaw.user.table.entity.TRole;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.PageRespone;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@Api(tags = "权限管理")
@RequestMapping("/admin/{eCode}/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private TRoleService tRoleService;

    @Autowired
    private TDepartmentService tDepartmentService;


    @ApiOperation(value = "查询部门", notes = "查询部门")
    @GetMapping("/findDepartment")
    public BaseResult findDepartment(TDepartment tDepartment){
        return roleService.findDepartment(tDepartment);
    }

    @ApiOperation(value = "保存部门", notes = "保存部门")
    @PostMapping("/saveDepartment")
    public BaseResult saveDepartment(@RequestBody TDepartment tDepartment){

        return BaseResult.ok();
    }

    @ApiOperation(value = "删除部门", notes = "删除部门")
    @GetMapping("/delDepartment")
    public BaseResult delDepartment(){

        return BaseResult.ok();
    }

    /**
     * =====================================================  查询角色
     */
    @ApiOperation(value = "查询角色", notes = "查询角色")
    @GetMapping("/findRole")
    public BaseResult<TRole> findRole(@RequestParam Map<String, Object> map) {
        PageRespone<TRole> page = tRoleService.findPage(new JSONObject(map));
        return BaseResult.ok(page);
    }

    @ApiOperation(value = "保存角色", notes = "保存角色")
    @PostMapping("/saveRole")
    public BaseResult<TRole> saveRole(@RequestParam Map<String, Object> map) {
        return BaseResult.ok();
    }

    @ApiOperation(value = "删除角色", notes = "删除角色")
    @GetMapping("/delRole")
    public BaseResult<TRole> delRole(@RequestParam Map<String, Object> map) {
        return BaseResult.ok();
    }

    /**
     * =====================================================  查询角色 下的 人员
     */

    @ApiOperation(value = "查询角色下的人员", notes = "查询角色下的人员")
    @GetMapping("/findAdmin")
    public BaseResult<TRole> findAdmin(@RequestParam Map<String, Object> map) {
        return BaseResult.ok();
    }

    @ApiOperation(value = "保存角色下的人员", notes = "保存角色下的人员")
    @PostMapping("/saveAdmin")
    public BaseResult<TRole> saveAdmin(@RequestParam Map<String, Object> map) {
        return BaseResult.ok();
    }

    @ApiOperation(value = "删除角色下的人员", notes = "删除角色下的人员")
    @GetMapping("/delAdmin")
    public BaseResult<TRole> delAdmin(@RequestParam Map<String, Object> map) {
        return BaseResult.ok();
    }

    /**
     * =====================================================  查询角色权限
     */
    @ApiOperation(value = "查询角色权限", notes = "查询角色权限")
    @GetMapping("/findPower")
    public BaseResult<TRole> findPower(@RequestParam Map<String, Object> map) {
        return BaseResult.ok();
    }



}
