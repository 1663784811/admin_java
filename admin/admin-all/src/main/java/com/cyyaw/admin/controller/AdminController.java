package com.cyyaw.admin.controller;

import cn.hutool.json.JSONObject;
import com.cyyaw.user.config.TokenData;
import com.cyyaw.user.service.TAdminService;
import com.cyyaw.user.table.entity.TAdmin;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.PageRespone;
import com.cyyaw.util.tools.WhyStringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "后台管理员")
@RequestMapping("/admin/{eCode}/admin")
public class AdminController {

    @Autowired
    private TAdminService tAdminService;



    @ApiOperation(value = "添加或修改", notes = "添加或修改")
    @PostMapping("/saveTAdmin")
    public BaseResult saveTAdmin(@RequestBody TAdmin saveObj,  @TokenData LoginInfo loginInfo) {
        TAdmin obj = null;
        Integer id = saveObj.getId();
        if (ObjectUtils.isEmpty(id)) {
            //添加
            saveObj.setCreateTime(new Date());
            saveObj.setTid(WhyStringUtil.getUUID());
            saveObj.setEnterpriseCode(loginInfo.getEnterpriseCode());
            log.info("添加:{}", saveObj);
            obj = tAdminService.save(saveObj);
        } else {
            //修改
            log.info("修改:{}", saveObj);
            TAdmin cpObj = tAdminService.findId(id);
            Assert.notNull(cpObj, "操作失败！");
            BeanUtils.copyProperties(saveObj, cpObj);
            obj = tAdminService.save(cpObj);
        }
        return BaseResult.ok(obj);
    }

}
