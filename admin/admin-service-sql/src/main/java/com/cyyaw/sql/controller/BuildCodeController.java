package com.cyyaw.sql.controller;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cyyaw.sql.buildcode.croe.database.DataBase;
import com.cyyaw.sql.buildcode.croe.entity.java.JavaColumn;
import com.cyyaw.sql.buildcode.croe.entity.java.JavaData;
import com.cyyaw.sql.buildcode.croe.entity.vue.Filters;
import com.cyyaw.sql.buildcode.croe.entity.vue.VueJson;
import com.cyyaw.sql.buildcode.croe.tools.OperationTools;
import com.cyyaw.sql.buildcode.croe.tools.TypeTools;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Slf4j

@Api(tags = "构建代码")
@RequestMapping("/admin/buildCode")
@RestController
public class BuildCodeController {


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @GetMapping("/loadTable")
    @ApiOperation(value = "加载数据表格", notes = "加载数据表格")
    public BaseResult loadTable() throws SQLException, ClassNotFoundException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        DataBase dataBase = new DataBase(connection);
        List<JavaData> tableList = dataBase.getTableList(null);

        JSONArray arr = new JSONArray();

        for (int i = 0; i < tableList.size(); i++) {
            JavaData javaData = tableList.get(i);
            JSONObject rest = new JSONObject();
            // =====================================================================
            rest.set("newTable", newTableFn(javaData));
            rest.set("selectData", selectDataFn(javaData));
            rest.set("dataTree", pageFormFn(javaData));
            rest.set("pageForm", pageFormFn(javaData));
            // =====================================================================
            JSONObject jsobj = new JSONObject();
            jsobj.set("pageConfig", rest);
            jsobj.set("table", javaData.getTable());
            jsobj.set("database", javaData.getDatabase());
            jsobj.set("note", javaData.getTableNote());
            arr.add(jsobj);
        }
        return BaseResult.ok(arr);
    }


    private JSONObject selectDataFn(JavaData javaData) {

        JSONObject selectObj = new JSONObject();
        selectObj.set("queryRequest", getRequest("/admin/${eCode}/common/query"));
        selectObj.set("delRequest", getRequest("/admin/${eCode}/common/del"));

        JSONObject saveObj = new JSONObject();
        saveObj.set("saveRequest", getRequest("/admin/${eCode}/common/del"));

        JSONObject tableObj = new JSONObject();

        JSONObject js = new JSONObject();
        js.set("selectObj", selectObj);
        js.set("saveObj", saveObj);
        js.set("tableObj", tableObj);
        return js;
    }

    private JSONObject pageFormFn(JavaData javaData){
        List<JavaColumn> javaColumns = javaData.getJavaColumns();
        List<VueJson> vueJsons = TypeTools.javaColumnList2VueJsonList(javaColumns);
        JSONArray columns = tableObjFn(vueJsons);

        JSONObject queryObj = getRequest("/admin/${eCode}/common/query");
        JSONObject delObj = getRequest("/admin/${eCode}/common/del");

        JSONObject addObj = new JSONObject();
        addObj.set("show", false);
        addObj.set("loading", true);
        addObj.set("data", new JSONObject());
        addObj.set("columns", columns);

        JSONObject js = new JSONObject();
        js.set("queryObj", queryObj);
        js.set("addObj", addObj);
        js.set("updateObj", addObj);
        js.set("delObj", delObj);
        return js;
    }

    private JSONObject newTableFn(JavaData javaData) {


        List<JavaColumn> javaColumns = javaData.getJavaColumns();
        List<VueJson> vueJsons = TypeTools.javaColumnList2VueJsonList(javaColumns);
        for (int j = 0; j < vueJsons.size(); j++) {
            VueJson vueJson = vueJsons.get(j);
            List<Filters> filters = vueJson.getFilters();
            if (null != filters) {
                if (filters.size() == 0) {
                    vueJson.setFilters(null);
                }
            }
            String key = vueJson.getKey();
            String message = vueJson.getMessage();
            if ("id".equals(key)) {
                vueJson.setWidth(60);
                vueJson.setControlType("hidden");
            }
            if ("del".equals(key)) {
                vueJsons.remove(j);
                j--;
                continue;
            }
            if ("tid".equals(key)) {
                vueJson.setTooltip(true);
                vueJson.setIsShowColumn(false);
                vueJson.setControlType("hidden");
            }
            if ("createTime".equals(key)) {
                vueJson.setWidth(160);
                vueJson.setControlType("hidden");
            }
            if (null != message && message.indexOf("名") != -1) {
                vueJson.setIsShowSearch(true);
            }
        }

        // =================    搜索
        JSONObject searchObj = new JSONObject();
        searchObj.set("show", true);
        searchObj.set("columns", searchObjFn(vueJsons));

        // =================  表格
        JSONObject tableObj = new JSONObject();

        tableObj.set("queryRequest", getRequest("/admin/${eCode}/common/query"));
        tableObj.set("delRequest", getRequest("/admin/${eCode}/common/del"));

        tableObj.set("columns", tableObjFn(vueJsons));
        tableObj.set("loading", true);
        tableObj.set("showColumns", false);
        tableObj.set("operationObj", new JSONObject());

        // =================  保存
        JSONObject saveObj = new JSONObject();
        saveObj.set("show", false);
        saveObj.set("loading", true);
        saveObj.set("editor", true);
        saveObj.set("data", new JSONObject());
        saveObj.set("columns", tableObjFn(vueJsons));


        JSONObject js = new JSONObject();
        js.set("searchObj", searchObj);
        js.set("tableObj", tableObj);
        js.set("saveObj", saveObj);
        js.set("updateObj", saveObj);
        return js;
    }


    private JSONArray tableObjFn(List<VueJson> vueJsons) {
        return JSONUtil.parseArray(JSONUtil.toJsonStr(vueJsons));
    }


    private JSONArray searchObjFn(List<VueJson> vueJsons) {
        String search = "{\n" +
                "        even: 'search',\n" +
                "        name: '搜索',\n" +
                "        show: true,\n" +
                "        icon: 'ios-search',\n" +
                "        type: 'success',\n" +
                "        url: '',\n" +
                "        parameter: []\n" +
                "      }";


        JSONObject searchObj = new JSONObject(search);
        searchObj.set("parameter", tableObjFn(vueJsons));

        String save = "{\n" +
                "        even: 'save',\n" +
                "        name: '添加',\n" +
                "        show: true,\n" +
                "        icon: 'ios-search',\n" +
                "        type: 'warning',\n" +
                "        url: '',\n" +
                "        parameter: []\n" +
                "      }";


        JSONObject saveObj = new JSONObject(save);


        String del = "{\n" +
                "        even: 'del',\n" +
                "        name: '删除',\n" +
                "        show: true,\n" +
                "        icon: 'ios-search',\n" +
                "        type: 'error',\n" +
                "        url: '',\n" +
                "        parameter: []\n" +
                "      }";
        JSONObject delObj = new JSONObject(del);

        JSONArray arr = new JSONArray();
        arr.add(searchObj);
        arr.add(saveObj);
        arr.add(delObj);
        return arr;
    }


    private JSONObject getRequest(String url) {
        JSONObject queryRequest = new JSONObject();
        queryRequest.set("url", url);
        JSONObject parameter = new JSONObject();
        parameter.set("code", "");
        queryRequest.set("parameter", parameter);
        return queryRequest;
    }

}
