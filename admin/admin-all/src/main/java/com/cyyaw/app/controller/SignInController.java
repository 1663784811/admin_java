package com.cyyaw.app.controller;


import cn.hutool.json.JSONObject;
import com.cyyaw.signin.service.SiSignInService;
import com.cyyaw.signin.service.SiSignLogService;
import com.cyyaw.signin.table.entity.SiSignIn;
import com.cyyaw.signin.table.entity.SiSignLog;
import com.cyyaw.user.config.TokenData;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.PageRespone;
import com.cyyaw.util.tools.WhyStringUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
@Api(tags = "签到")
@RequestMapping("/app/{appId}/si/signIn")
@RestController
public class SignInController {


    @Autowired
    private SiSignInService siSignInService;

    @Autowired
    private SiSignLogService siSignLogService;

    /**
     * 分页条件查询
     */
    @GetMapping("/findPage")
    public BaseResult<SiSignIn> findPageTAdmin(@RequestParam Map<String, Object> map, @TokenData LoginInfo loginInfo) {
        String appId = loginInfo.getAppId();
        map.put("eq_appId", appId);
        PageRespone<SiSignIn> page = siSignInService.findPage(new JSONObject(map));
        return BaseResult.ok(page);
    }


    /**
     * 根据ID查询
     */
    @GetMapping("/findIdSiSignIn")
    public BaseResult findSiSignIn(String tid) {
        SiSignIn obj = siSignInService.findByTid(tid);
        List<SiSignLog> signLogList = siSignLogService.findBySignInId(tid);
        obj.setSignLogList(signLogList);
        return BaseResult.ok(obj);
    }


    /**
     * 添加或修改
     */
    @PostMapping("/saveSiSignIn")
    public BaseResult saveSiSignIn(@RequestBody SiSignIn saveObj, @TokenData LoginInfo loginInfo) {
        SiSignIn obj = null;
        Integer id = saveObj.getId();
        String appId = loginInfo.getAppId();
        if (ObjectUtils.isEmpty(id)) {
            //添加
            saveObj.setCreateTime(new Date());
            saveObj.setTid(WhyStringUtil.getUUID());
            saveObj.setAppId(appId);
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


    @PostMapping("/signInLogSave")
    public BaseResult signInLogSave(@RequestBody SiSignLog signLog) {
        SiSignLog save = siSignLogService.save(signLog);
        return BaseResult.ok(save);
    }

}
