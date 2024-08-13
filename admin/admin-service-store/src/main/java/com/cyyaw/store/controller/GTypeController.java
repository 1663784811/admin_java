package com.cyyaw.store.controller;

import cn.hutool.json.JSONObject;
import com.cyyaw.store.service.GTypeService;
import com.cyyaw.store.table.goods.entity.GType;
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
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "商品品类")
@RequestMapping("/admin/gType")
public class GTypeController {

    @Autowired
    private GTypeService gTypeService;


    @ApiOperation(value = "品类列表树", notes = "品类列表树")
    @GetMapping("/findTree")
    public BaseResult<GType> findTree(@RequestParam Map<String, Object> map) {
        List<GType> gTypeList = gTypeService.findTree(new JSONObject(map));
        return BaseResult.ok(gTypeList);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/findIdGType")
    public BaseResult findIdGType(Integer id) {
        GType obj = gTypeService.findId(id);
        return BaseResult.ok(obj);
    }


    /**
     * 添加或修改
     */
    @PostMapping("/saveGType")
    public BaseResult saveGType(@RequestBody GType saveObj) {
        return BaseResult.ok(gTypeService.saveTree(saveObj));
    }

    /**
     * 删除
     */
    @PostMapping("/delGType")
    public BaseResult delGType(@RequestBody GType obj) {
        Integer id = obj.getId();
        if (null != id) {
            gTypeService.delTree(id);
        }
        return BaseResult.ok("删除成功");
    }

}
