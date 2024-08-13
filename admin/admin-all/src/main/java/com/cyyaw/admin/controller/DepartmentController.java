package com.cyyaw.admin.controller;

import cn.hutool.json.JSONObject;
import com.cyyaw.user.config.TokenData;
import com.cyyaw.user.service.TDepartmentService;
import com.cyyaw.user.table.entity.TDepartment;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.util.tools.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequestMapping("/admin/{eCode}/department")
@RestController
public class DepartmentController {

    @Autowired
    private TDepartmentService tDepartmentService;



    /**
     * 分页条件查询
     */
    @GetMapping("/findDepartmentTree")
    public BaseResult findDepartmentTree(@RequestParam Map<String, Object> map, @TokenData LoginInfo loginInfo) {
        String eId = loginInfo.getEnterpriseCode();
        map.put("eq_enterpriseCode", eId);
        return BaseResult.ok(tDepartmentService.findTree(new JSONObject(map)));
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/findIdTDepartment")
    public BaseResult findIdTDepartment(Integer id) {
        TDepartment obj = tDepartmentService.findId(id);
        return BaseResult.ok(obj);
    }


    /**
     * 添加或修改
     */
    @PostMapping("/saveTDepartment")
    public BaseResult saveTDepartment(@RequestBody TDepartment saveObj,  @TokenData LoginInfo loginInfo) {
        String eId = loginInfo.getEnterpriseCode();
        saveObj.setEnterpriseCode(eId);
        TDepartment obj = tDepartmentService.saveTree(saveObj);
        return BaseResult.ok(obj);
    }

    /**
     * 删除
     */
    @PostMapping("/delTDepartment")
    public BaseResult delTDepartment(@RequestBody TDepartment obj, @TokenData LoginInfo loginInfo) {
        Integer id = obj.getId();
        tDepartmentService.del(id);
        return BaseResult.ok("删除成功");
    }

}
