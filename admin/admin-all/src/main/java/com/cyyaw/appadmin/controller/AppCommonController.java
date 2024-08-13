package com.cyyaw.appadmin.controller;

import cn.hutool.json.JSONObject;
import com.cyyaw.sql.table.dao.*;
import com.cyyaw.spider.table.dao.TagDao;
import com.cyyaw.user.config.TokenData;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.util.SqlCommonUtil;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Api(tags = "公共模块")
@RestController
@RequestMapping("/appAdmin/{appId}/common")
public class AppCommonController {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private CPageDao cPageDao;

    @Autowired
    private CPageComponentsDao cPageComponentsDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private CFieldDao cFieldDao;

    @Autowired
    private CSqlDao cSqlDao;


    /**
     * {
     * code: '',
     * page: 1,
     * size: 30,
     * sort: ''
     * }
     */
    @ApiOperation(value = "通用查询")
    @GetMapping("/query")
    public BaseResult query(@RequestBody @RequestParam Map<String, Object> map, @TokenData LoginInfo loginInfo) {
        JSONObject json = new JSONObject(map);
        return commonDao.query(SqlCommonUtil.setDefaultData(json, loginInfo));
    }


    /**
     * {
     * code: 'key',
     * data:{数据}
     * }
     */
    @ApiOperation(value = "通用保存")
    @PostMapping("/save")
    public BaseResult save(@RequestBody Map<String, Object> map) {
        JSONObject json = new JSONObject(map);
        String code = json.getStr("code");
        JSONObject data = json.getJSONObject("data");
        return commonDao.save(code, data);
    }


    /**
     * {
     * code: 'key'
     * }
     */
    @ApiOperation(value = "通用删除")
    @PostMapping("/del")
    public BaseResult del(@RequestBody Map<String, Object> map, @TokenData LoginInfo loginInfo) {
        JSONObject json = new JSONObject(map);
        String code = json.getStr("code");
        JSONObject data = json.getJSONObject("data");
        return commonDao.del(code, data);
    }

}
