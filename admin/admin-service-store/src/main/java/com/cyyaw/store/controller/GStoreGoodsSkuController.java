package com.cyyaw.store.controller;

import cn.hutool.json.JSONObject;
import com.cyyaw.store.service.GStoreGoodsSkuService;
import com.cyyaw.store.table.goods.entity.GStoreGoodsSku;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.PageRespone;
import com.cyyaw.util.tools.WhyStringUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.ObjectUtils;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Date;

@Slf4j
@RestController
@Api(tags = "商品SKU")
@RequestMapping("/admin/gStoreGoodsSku")
public class GStoreGoodsSkuController {

    @Autowired
    private GStoreGoodsSkuService gStoreGoodsSkuService;

    /**
     * 分页条件查询
     */
    @GetMapping("/findPage")
    public BaseResult<GStoreGoodsSku> findPageGStoreGoodsSku(@RequestParam Map<String, Object> map) {
        PageRespone<GStoreGoodsSku> page = gStoreGoodsSkuService.findPage(new JSONObject(map));
        return BaseResult.ok(page);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/findIdGStoreGoodsSku")
    public BaseResult findIdGStoreGoodsSku(Integer id) {
        GStoreGoodsSku obj = gStoreGoodsSkuService.findId(id);
        return BaseResult.ok(obj);
    }


    /**
     * 添加或修改
     */
    @PostMapping("/saveGStoreGoodsSku")
    public BaseResult saveGStoreGoodsSku(@RequestBody GStoreGoodsSku saveObj) {
        GStoreGoodsSku obj = null;
        Integer id = saveObj.getId();
        if (ObjectUtils.isEmpty(id)) {
            //添加
            saveObj.setCreateTime(new Date());
            saveObj.setTid(WhyStringUtil.getUUID());
            saveObj.setNumber(saveObj.getNumber() == null ? 0 : saveObj.getNumber());
            log.info("添加:{}", saveObj);
            obj = gStoreGoodsSkuService.save(saveObj);
        } else {
            //修改
            log.info("修改:{}", saveObj);
            GStoreGoodsSku cpObj = gStoreGoodsSkuService.findId(id);
            Assert.notNull(cpObj, "操作失败！");
            BeanUtils.copyProperties(saveObj, cpObj);
            obj = gStoreGoodsSkuService.save(cpObj);
        }
        return BaseResult.ok(obj);
    }

    /**
     * 删除
     */
    @PostMapping("/delGStoreGoodsSku")
    public BaseResult delGStoreGoodsSku(@RequestBody Integer idArr[]) {
        gStoreGoodsSkuService.del(idArr);
        return BaseResult.ok("删除成功");
    }

}
