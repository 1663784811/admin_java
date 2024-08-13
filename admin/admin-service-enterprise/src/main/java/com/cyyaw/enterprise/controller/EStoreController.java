package com.cyyaw.enterprise.controller;

import cn.hutool.json.JSONObject;
import com.cyyaw.enterprise.service.EStoreService;
import com.cyyaw.enterprise.table.entity.EStore;
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
@RequestMapping("/admin/eStore")
@RestController
public class EStoreController {

    @Autowired
    private EStoreService eStoreService;

    /**
     * 分页条件查询
     */
    @GetMapping("/findPage")
    public BaseResult<EStore> findPageEStore(@RequestParam Map<String, Object> map) {
        PageRespone<EStore> page = eStoreService.findPage(new JSONObject(map));
        return BaseResult.ok(page);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/findIdEStore")
    public BaseResult findIdEStore(Integer id) {
        EStore obj = eStoreService.findId(id);
        return BaseResult.ok(obj);
    }

}
