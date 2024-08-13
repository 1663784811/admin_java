package com.cyyaw.store.controller;

import cn.hutool.json.JSONObject;
import com.cyyaw.store.service.GGoodsService;
import com.cyyaw.store.table.goods.entity.GGoods;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.PageRespone;
import com.cyyaw.util.tools.WhyStringUtil;
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
@RequestMapping("/admin/gGoods")
@RestController
public class GGoodsController {

    @Autowired
    private GGoodsService gGoodsService;

    /**
     * 分页条件查询
     */
    @GetMapping("/findPage")
    public BaseResult<GGoods> findPageGGoods(@RequestParam Map<String, Object> map) {
        PageRespone<GGoods> page = gGoodsService.findPage(new JSONObject(map));
        return BaseResult.ok(page);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/findIdGGoods")
    public BaseResult findIdGGoods(String tid) {
        GGoods obj = gGoodsService.findByTid(tid);
        return BaseResult.ok(obj);
    }


    /**
     * 添加或修改
     */
    @PostMapping("/saveGGoods")
    public BaseResult saveGGoods(@RequestBody GGoods saveObj) {
       return gGoodsService.saveGGoods(saveObj);
    }

    /**
     * 删除
     */
    @PostMapping("/delGGoods")
    public BaseResult delGGoods(@RequestBody Integer idArr[]) {
        gGoodsService.del(idArr);
        return BaseResult.ok("删除成功");
    }

}
