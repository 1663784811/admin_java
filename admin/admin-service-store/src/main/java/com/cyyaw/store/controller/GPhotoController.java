package com.cyyaw.store.controller;

import cn.hutool.json.JSONObject;
import com.cyyaw.store.service.GPhotoService;
import com.cyyaw.store.table.goods.entity.GPhoto;
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
@RequestMapping("/admin/gPhoto")
@RestController
public class GPhotoController {

    @Autowired
    private GPhotoService gPhotoService;

    /**
     * 分页条件查询
     */
    @GetMapping("/findPage")
    public BaseResult<GPhoto> findPageGPhoto(@RequestParam Map<String, Object> map) {
        PageRespone<GPhoto> page = gPhotoService.findPage(new JSONObject(map));
        return BaseResult.ok(page);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/findIdGPhoto")
    public BaseResult findIdGPhoto(Integer id) {
        GPhoto obj = gPhotoService.findId(id);
        return BaseResult.ok(obj);
    }


    /**
     * 添加或修改
     */
    @PostMapping("/saveGPhoto")
    public BaseResult saveGPhoto(@RequestBody GPhoto saveObj) {
        GPhoto obj = null;
        Integer id = saveObj.getId();
        if (ObjectUtils.isEmpty(id)) {
            //添加
            saveObj.setCreateTime(new Date());
             saveObj.setTid(WhyStringUtil.getUUID());
            log.info("添加:{}", saveObj);
            obj = gPhotoService.save(saveObj);
        } else {
            //修改
            log.info("修改:{}", saveObj);
            GPhoto cpObj = gPhotoService.findId(id);
            Assert.notNull(cpObj, "操作失败！");
            BeanUtils.copyProperties(saveObj,cpObj);
            obj = gPhotoService.save(cpObj);
        }
        return BaseResult.ok(obj);
    }

    /**
     * 删除
     */
    @PostMapping("/delGPhoto")
    public BaseResult delGPhoto(@RequestBody Integer idArr[]) {
        gPhotoService.del(idArr);
        return BaseResult.ok("删除成功");
    }

}
