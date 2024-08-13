package com.cyyaw.admin.controller;

import com.cyyaw.user.service.UUserService;
import com.cyyaw.user.table.entity.UUser;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.WhyStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RequestMapping("/admin/{eCode}/user")
@RestController
public class UserController {

    @Autowired
    private UUserService uUserService;

    /**
     * 添加或修改
     */
    @PostMapping("/saveUUser")
    public BaseResult saveUUser(@RequestBody UUser saveObj) {
        UUser obj = null;
        Integer id = saveObj.getId();
        if (ObjectUtils.isEmpty(id)) {
            //添加
            saveObj.setCreateTime(new Date());
            saveObj.setTid(WhyStringUtil.getUUID());
            log.info("添加:{}", saveObj);
            obj = uUserService.save(saveObj);
        } else {
            //修改
            log.info("修改:{}", saveObj);
            UUser cpObj = uUserService.findId(id);
            Assert.notNull(cpObj, "操作失败！");
            BeanUtils.copyProperties(saveObj,cpObj);
            obj = uUserService.save(cpObj);
        }
        return BaseResult.ok(obj);
    }

}
