package com.cyyaw.tx;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cyyaw.data.PageSettingData;
import com.cyyaw.sql.service.CPageComponentsLogService;
import com.cyyaw.sql.service.CPageComponentsObjService;
import com.cyyaw.sql.service.CPageComponentsService;
import com.cyyaw.sql.service.CPageService;
import com.cyyaw.sql.table.entity.CPage;
import com.cyyaw.sql.table.entity.CPageComponents;
import com.cyyaw.sql.table.entity.CPageComponentsLog;
import com.cyyaw.sql.table.entity.CPageComponentsObj;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.WhyStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/tx/config/page")
public class PageSettingController {

    @Autowired
    private CPageService cPageService;

    @Autowired
    private CPageComponentsService cPageComponentsService;

    @Autowired
    private CPageComponentsLogService cPageComponentsLogService;

    @Autowired
    private CPageComponentsObjService cPageComponentsObjService;

    @Autowired
    private PageSettingData pageSettingData;


    /**
     * 获取页面配置
     */
    @GetMapping("/pageSetting")
    public BaseResult pageSetting(@RequestParam Map<String, Object> map) {
        JSONObject json = new JSONObject(map);
        String pageCode = json.getStr("pageCode");
        String url = json.getStr("url");
        //================= 收集请求地址数据
        log.info("========收集请求地址数据: {}", url);
        //=================
        CPage cPage = cPageService.findByPageCode(pageCode);
        if (null != cPage) {
            List<CPageComponents> components = cPage.getComponents();
            return pageSettingData(components);
        } else {
            return BaseResult.fail("找不到页面数据");
        }
    }

    @GetMapping("/findSetting")
    public BaseResult findSetting(CPageComponents obj) {
        List<CPageComponents> components = cPageComponentsService.findByExample(obj);
        return pageSettingData(components);
    }

    /**
     * 处理页面数据
     */
    private BaseResult pageSettingData(List<CPageComponents> components) {
        JSONObject rest = new JSONObject();
        for (int i = 0; i < components.size(); i++) {
            CPageComponents cPageComponents = components.get(i);
            String data = getJsonData(cPageComponents.getTid());
            String componentsCode = cPageComponents.getComponentsCode();
            if (StrUtil.isBlank(componentsCode)) componentsCode = "commonTable";
            if (StrUtil.isBlank(data)) data = "{}";
            // 查询组件模块数据
            JSONObject js = new JSONObject(data);
            js.set("id", cPageComponents.getId());
            js.set("tid", cPageComponents.getTid());
            js.set("type", cPageComponents.getType() == null ? "commonTable" : cPageComponents.getType());
            js.set("pageId", cPageComponents.getPageId() == null ? "" : cPageComponents.getPageId());
            // 通用表格
            rest.set(componentsCode, js);
        }
        return BaseResult.ok(rest);
    }


    /**
     * 保存组件数据
     *
     * @return
     */
    @PostMapping("/saveComponents")
    public BaseResult saveComponents(@RequestBody CPageComponents components) {
        CPageComponents obj = cPageComponentsService.findId(components.getId());
        if (null != obj) {
            // 记录日志
            String data = obj.getData();
            String tid = obj.getTid();
            CPageComponentsLog infoLog = new CPageComponentsLog();
            infoLog.setTid(WhyStringUtil.getUUID());
            infoLog.setCreateTime(new Date());
            infoLog.setNote("");
            infoLog.setPageComponentsId(tid);
            infoLog.setData(data);
            cPageComponentsLogService.save(infoLog);
            // 修改数据
            String newData = components.getData();
            obj.setData(newData);
            cPageComponentsService.save(obj);
            pageSettingData.saveToComponentsObj(obj);
        } else {
            return BaseResult.fail("找不到数据:" + JSONUtil.toJsonStr(new JSONObject(components)));
        }
        return BaseResult.ok();
    }

    @GetMapping("/commonTableToNewTable")
    public BaseResult commonTableToNewTable(Integer id) {
        pageSettingData.commonTableToNewTable(id);
        return BaseResult.ok();
    }



    private String getJsonData(String componentsId) {
        JSONObject json = new JSONObject();
        CPageComponentsObj componentsObj = new CPageComponentsObj();
        componentsObj.setPageComponentsId("" + componentsId);
        List<CPageComponentsObj> all = cPageComponentsObjService.findByExample(componentsObj);
        for (int i = 0; i < all.size(); i++) {
            CPageComponentsObj obj = all.get(i);
            String data = obj.getData();
            if (StrUtil.isNotBlank(data)) {
                if (data.indexOf("[") == 0) {
                    json.set(obj.getDataKey(), new JSONArray(data));
                } else {
                    json.set(obj.getDataKey(), new JSONObject(data));
                }
            }
        }
        return json.toString();
    }


}
