package com.cyyaw.sql.controller;

import com.cyyaw.sql.service.CPageService;
import com.cyyaw.sql.table.entity.CPage;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.WhyStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@Slf4j
@RestController
@RequestMapping("/admin/config/page")
public class CPageController {

    @Autowired
    private CPageService cPageService;

    /**
     * 添加或修改
     */
    @PostMapping("/saveCPage")
    public BaseResult saveCPage(@RequestBody CPage saveObj) {
        CPage obj = null;
        Integer id = saveObj.getId();
        if (ObjectUtils.isEmpty(id)) {
            //添加
            saveObj.setCreateTime(new Date());
            saveObj.setTid(WhyStringUtil.getUUID());
            log.info("添加:{}", saveObj);
            obj = cPageService.save(saveObj);
        } else {
            //修改
            log.info("修改:{}", saveObj);
            CPage cpObj = cPageService.findId(id);
            Assert.notNull(cpObj, "操作失败！");
            BeanUtils.copyProperties(saveObj, cpObj);
            obj = cPageService.save(cpObj);
        }
        return BaseResult.ok(obj);
    }


    /**
     * 复制新页面
     */
    @PostMapping("/copyCPage")
    public BaseResult copyCPage(@RequestBody CPage cPage) {
        cPageService.copyCPage(cPage);
        return BaseResult.ok();
    }

}
