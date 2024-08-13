package com.cyyaw.data;

import java.util.ArrayList;
import java.util.Date;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cyyaw.sql.service.CPageComponentsObjService;
import com.cyyaw.sql.service.CPageComponentsService;
import com.cyyaw.sql.table.dao.CPageComponentsDao;
import com.cyyaw.sql.table.dao.CPageComponentsObjDao;
import com.cyyaw.sql.table.entity.CPageComponents;
import com.cyyaw.sql.table.entity.CPageComponentsObj;
import com.cyyaw.util.tools.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 数据迁移工具 (  页面配置数据  )
 */

@Component
public class PageSettingData {


    @Autowired
    private CPageComponentsService cPageComponentsService;

    @Autowired
    private CPageComponentsDao cPageComponentsDao;

    @Autowired
    private CPageComponentsObjService cPageComponentsObjService;


    public void pageComponentsToComponentsObj() {
        //
        List<CPageComponents> componentsList = cPageComponentsService.findAll(new JSONObject());
        for (int i = 0; i < componentsList.size(); i++) {
            CPageComponents cPageComponents = componentsList.get(i);
            saveToComponentsObj(cPageComponents);
        }
    }


    public void saveToComponentsObj(CPageComponents components) {
        // 第一步:清空数据
        CPageComponentsObj componentsObj = new CPageComponentsObj();
        componentsObj.setPageComponentsId("" + components.getTid());
        List<CPageComponentsObj> all = cPageComponentsObjService.findByExample(componentsObj);
        for (int i = 0; i < all.size(); i++) {
            cPageComponentsObjService.del(all.get(i).getId());
        }
        String data = components.getData();
        String name = components.getName();
        String tid = components.getTid();
        if (StrUtil.isNotBlank(data)) {
            JSONObject json = new JSONObject(data);
            for (String key : json.keySet()) {
                Object ob = json.getObj(key);
                if (ob instanceof JSONArray) {
                    JSONArray array = json.getJSONArray(key);
                    CPageComponentsObj obj = new CPageComponentsObj();
                    obj.setPageComponentsId(tid);
                    obj.setName(name);
                    obj.setDataKey(key);
                    obj.setData(array.toString());
                    cPageComponentsObjService.save(obj);
                } else {
                    JSONObject jsonObject = json.getJSONObject(key);
                    CPageComponentsObj obj = new CPageComponentsObj();
                    obj.setPageComponentsId(tid);
                    obj.setName(name);
                    obj.setDataKey(key);
                    obj.setData(jsonObject.toString());
                    cPageComponentsObjService.save(obj);
                }
            }
        }
    }

    public void commonTableToNewTable(Integer id) {
        CPageComponents obj = cPageComponentsService.findId(id);
        if (null != obj) {
            String type = obj.getType();
            if (type == null || "commonTable".equals(type)) {
                obj.setType("newTable");
                obj.setComponentsCode("newTable");
                String data = obj.getData();
                obj.setData(commonTableToNewTableData(data));
                cPageComponentsService.save(obj);
                saveToComponentsObj(obj);
            }
        }
    }


    public String commonTableToNewTableData(String data) {
        JSONObject json = new JSONObject(data);
        JSONObject requestObj = json.getJSONObject("requestObj");
        JSONObject queryRequest = requestObj.getJSONObject("queryRequest");
        JSONObject saveRequest = requestObj.getJSONObject("saveRequest");
        JSONObject delRequest = requestObj.getJSONObject("delRequest");
        JSONArray columns = json.getJSONArray("columns");
        JSONObject searchObj = new JSONObject();
        searchObj.set("show", true);
        searchObj.set("columns", new ArrayList<>());
        //
        JSONObject tableObj = new JSONObject();
        tableObj.set("queryRequest", queryRequest);
        tableObj.set("delRequest", delRequest);
        tableObj.set("columns", columns);
        tableObj.set("loading", true);
        tableObj.set("showColumns", false);
        tableObj.set("operation", new JSONObject());
        //
        JSONObject saveObj = new JSONObject();
        saveObj.set("show", false);
        saveObj.set("loading", true);
        saveObj.set("editor", true);
        saveObj.set("editor", true);
        saveObj.set("url", saveRequest.get("url"));
        saveObj.set("data", new JSONObject());
        saveObj.set("columns", columns);
        // ===
        JSONObject restJson = new JSONObject();
        restJson.set("searchObj", searchObj);
        restJson.set("tableObj", tableObj);
        restJson.set("saveObj", saveObj);

        return JSONUtil.toJsonStr(restJson);
    }


}
