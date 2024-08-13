package com.cyyaw.sql.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.cyyaw.sql.buildcode.croe.database.DataBase;
import com.cyyaw.sql.buildcode.croe.entity.java.JavaColumn;
import com.cyyaw.sql.buildcode.croe.entity.java.JavaData;
import com.cyyaw.sql.service.SqlService;
import com.cyyaw.sql.table.entity.CSql;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(tags = "SQL配置模块")
@RequestMapping("/admin/common/sql")
@RestController
public class SqlController {

    @Autowired
    private SqlService sqlService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/sqlList")
    public BaseResult<CSql> sqlList(@RequestParam Map<String, Object> map) {
        return sqlService.sqlList(new JSONObject(map));
    }

    @PostMapping("/saveSql")
    public BaseResult saveSql(@RequestBody CSql cSql) {
        CSql save = sqlService.save(cSql);
        return BaseResult.ok(save);
    }

    @PostMapping("/delSql")
    public BaseResult delSql(@RequestBody Integer idArr[]) {
        sqlService.delSql(idArr);
        return BaseResult.ok("删除成功");
    }


    /**
     * 加载数据表字段
     *
     * @return
     */
    @GetMapping("/loadColumn")
    public BaseResult loadColumn(String table) throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        DataBase dataBase = new DataBase(connection);
        JavaData tableList = dataBase.getTable(table);
        JSONObject rest = new JSONObject();
        if (null != tableList) {
            List<String> query = new ArrayList<>();
            StringBuffer save = new StringBuffer("insert into  " + table + " (");
            int saveLength = save.length();
            StringBuffer saveValue = new StringBuffer("");
            StringBuffer update = new StringBuffer("update table " + table);
            int updateLength = update.length();
            StringBuffer del = new StringBuffer("delete from " + table + " where id = [id]");
            List<JavaColumn> columnList = tableList.getJavaColumns();
            for (int i = 0; i < columnList.size(); i++) {
                JavaColumn javaColumn = columnList.get(i);
                String javaType = javaColumn.getJavaType();
                String str = "";
                String javaField = javaColumn.getJavaField();
                String columnStr = javaColumn.getColumnName();
                Boolean isPrimary = javaColumn.getIsPrimary();
                if ("Integer".equals(javaType)) {
                    str = ("[eq_" + javaField + ":=" + columnStr + "]");
                } else if ("String".equals(javaType)) {
                    str = ("[%lk_" + javaField + ":=" + columnStr + "]");
                }
                if (StrUtil.isNotBlank(str)) {
                    if (!query.isEmpty()) {
                        query.add(" and " + str);
                    } else {
                        query.add(str);
                    }

                    // 保存
                    if (!isPrimary && !"del".equals(javaField)) {
                        if (save.length() > saveLength) {
                            save.append(",`" + columnStr + "`");
                            saveValue.append(",[" + javaField + "]");
                        } else {
                            save.append("`" + columnStr + "`");
                            saveValue.append("[" + javaField + "]");
                        }
                    }
                    // 更新
                    if (update.length() > updateLength) {
                        update.append(" ,`" + columnStr + "` = [" + javaField + "]");
                    } else {
                        update.append(" set `" + columnStr + "` = [" + javaField + "]");
                    }
                }
            }
            rest.set("query", query);
            rest.set("save", save + " ) values ( " + saveValue + " )");
            rest.set("update", update + " where id = [id]");
            rest.set("del", del);
        }
        return BaseResult.ok(rest);
    }

}
