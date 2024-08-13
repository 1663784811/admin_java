package com.cyyaw.appadmin.controller;


import cn.hutool.json.JSONObject;
import com.cyyaw.signin.service.SiSignInService;
import com.cyyaw.signin.table.entity.SiSignIn;
import com.cyyaw.user.config.TokenData;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.PageRespone;
import com.cyyaw.util.tools.WhyStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;


@Slf4j
@RequestMapping("/appAdmin/{appId}/siSign/admin")
@RestController
public class SiSignInController {


    @Autowired
    private SiSignInService siSignInService;


    /**
     * 分页条件查询
     */
    @GetMapping("/findPage")
    public BaseResult<SiSignIn> findPageTAdmin(@RequestParam Map<String, Object> map, @TokenData LoginInfo loginInfo) {
        String eCode = loginInfo.getEnterpriseCode();
        map.put("eCode", eCode);
        PageRespone<SiSignIn> page = siSignInService.findPage(new JSONObject(map));
        return BaseResult.ok(page);
    }



    /**
     * 根据ID查询
     */
    @GetMapping("/findIdSiSignIn")
    public BaseResult findSiSignIn(Integer id) {
        SiSignIn obj = siSignInService.findId(id);
        return BaseResult.ok(obj);
    }




    /**
     * 添加或修改
     */
    @PostMapping("/saveSiSignIn")
    public BaseResult saveSiSignIn(@RequestBody SiSignIn saveObj, @TokenData LoginInfo loginInfo) {
        SiSignIn obj = null;
        Integer id = saveObj.getId();
        if (ObjectUtils.isEmpty(id)) {
            //添加
            saveObj.setCreateTime(new Date());
            saveObj.setTid(WhyStringUtil.getUUID());
            log.info("添加:{}", saveObj);
            obj = siSignInService.save(saveObj);
        } else {
            //修改
            log.info("修改:{}", saveObj);
            SiSignIn cpObj = siSignInService.findId(id);
            Assert.notNull(cpObj, "操作失败！");
            BeanUtils.copyProperties(saveObj, cpObj);
            obj = siSignInService.save(cpObj);
        }
        return BaseResult.ok(obj);
    }



    /**
     * 删除
     */
    @PostMapping("/delTSiSignIn")
    public BaseResult delSiSignIn(@RequestBody Integer idArr[]) {
        siSignInService.del(idArr);
        return BaseResult.ok("删除成功");
    }



}
