package com.cyyaw.store.controller;

import cn.hutool.json.JSONObject;
import com.cyyaw.store.service.GCartService;
import com.cyyaw.store.table.goods.entity.GCart;
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
@RequestMapping("/admin/gCart")
@RestController
public class GCartController {

    @Autowired
    private GCartService gCarService;

    /**
     * 分页条件查询
     */
    @GetMapping("/findPage")
    public BaseResult<GCart> findPageGCar(@RequestParam Map<String, Object> map) {
        PageRespone<GCart> page = gCarService.findPage(new JSONObject(map));
        return BaseResult.ok(page);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/findIdGCar")
    public BaseResult findIdGCar(Integer id) {
        GCart obj = gCarService.findId(id);
        return BaseResult.ok(obj);
    }


    /**
     * 添加或修改
     */
    @PostMapping("/saveGCar")
    public BaseResult saveGCar(@RequestBody GCart saveObj) {
        GCart obj = null;
        Integer id = saveObj.getId();
        if (ObjectUtils.isEmpty(id)) {
            //添加
            saveObj.setCreateTime(new Date());
            saveObj.setTid(WhyStringUtil.getUUID());
            log.info("添加:{}", saveObj);
            obj = gCarService.save(saveObj);
        } else {
            //修改
            log.info("修改:{}", saveObj);
            GCart cpObj = gCarService.findId(id);
            Assert.notNull(cpObj, "操作失败！");
            BeanUtils.copyProperties(saveObj, cpObj);
            obj = gCarService.save(cpObj);
        }
        return BaseResult.ok(obj);
    }

    /**
     * 删除
     */
    @PostMapping("/delGCar")
    public BaseResult delGCar(@RequestBody Integer idArr[]) {
        gCarService.del(idArr);
        return BaseResult.ok("删除成功");
    }

}
