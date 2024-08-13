package com.cyyaw.sql.table.dao;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.cyyaw.config.exception.WebException;
import com.cyyaw.jpa.util.DataBaseUtils;
import com.cyyaw.jpa.util.entity.CommonSaveData;
import com.cyyaw.jpa.util.entity.FieldInfo;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.SqlUtils;
import com.cyyaw.util.tools.WebErrCodeEnum;
import com.cyyaw.util.tools.WhyStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Repository
public class CommonDaoImpl implements CommonDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * {
     * code: '',
     * page: '',
     * size: '',
     * }
     *
     * @param json
     * @return
     */
    @Override
    public BaseResult query(JSONObject json) {
        BaseResult rest = new BaseResult();
        //第一步：查询  sql 字符串
        String code = json.getStr("code");
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from c_sql c where c.status = 0 and c.tid = ?", code);
        if (sqlRowSet.next()) {
            String countsql = sqlRowSet.getString("count_sql");
            String sqlcontent = sqlRowSet.getString("content_sql");
            Integer login = sqlRowSet.getInt("login");
            if (null == login || login == 1) {
                String uId = json.getStr("__user_uId");
                if (StrUtil.isNotBlank(uId)) {
                    return query(countsql, sqlcontent, json, true);
                } else {
                    rest.setCode(-1);
                    rest.setMsg("未登录,状态不可用");
                }
            } else {
                return query(countsql, sqlcontent, json, true);
            }
        } else {
            rest.setCode(-1);
            rest.setMsg("找不到可用条件");
        }
        return rest;
    }

    /**
     * @param sqlCount
     * @param sqlcontent
     * @param json
     * @return
     */
    public BaseResult query(String sqlCount, String sqlcontent, JSONObject json, boolean touName) {
        Integer page = json.getInt("page");
        Integer size = json.getInt("size");
        String sort = json.getStr("sort");
        page = (page == null || page <= 0) ? 1 : page;
        size = (size == null || size <= 0) ? 30 : size;
        // ================
        if (StrUtil.isNotBlank(sort)) {
            if (sort.lastIndexOf("_desc") != -1) {
                String sortLine = StrUtil.toUnderlineCase(sort.substring(0, sort.lastIndexOf("_desc")));
                sqlcontent += " order by " + sortLine + " desc";
            } else {
                String sortLine = StrUtil.toUnderlineCase(sort);
                sqlcontent += " order by " + sortLine;
            }
        }
        String querySql = sqlcontent + " limit " + ((page - 1) * size + "," + size);
        String countSql = SqlUtils.explainSql(sqlCount, json);
        // 第二步：替换字符串
        log.info("统计sql语句: {} ", countSql);
        log.info("============================================");
        Integer total = jdbcTemplate.queryForObject(countSql, Integer.class);
        List<JSONObject> resData = query(querySql, json, touName);
        BaseResult rest = new BaseResult();
        rest.setCode(WebErrCodeEnum.WEB_SUCCESS.getCode());
        rest.setData(resData);
        rest.setMsg("ok");
        BaseResult.Result result = new BaseResult.Result();
        result.setTotal(Long.valueOf(total));
        result.setPage(page);
        result.setSize(size);
        rest.setResult(result);
        return rest;
    }

    /**
     * 通用查询
     */
    public List<JSONObject> query(String sqlContent, JSONObject json, boolean touName) {
        String querySql = SqlUtils.explainSql(sqlContent, json);
        log.info("执行sql语句: {} ", querySql);
        log.info("============================================");
        return execSql(querySql, touName, null);
    }

    /**
     * 执行sql语句
     */
    private List<JSONObject> execSql(String querySql, boolean touName, String[] val) {
        List<Map<String, Object>> data = jdbcTemplate.queryForList(querySql, val);
        List<JSONObject> resData = new ArrayList<>();
        if (null != data && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                Map<String, Object> mapObj = data.get(i);
                JSONObject jsObj = new JSONObject();
                for (String key : mapObj.keySet()) {
                    Object value = null;
                    Object dateTime = mapObj.get(key);
                    if (dateTime instanceof Timestamp) {
                        value = DateUtil.formatDateTime((Timestamp) dateTime);
                    } else {
                        value = dateTime;
                    }
                    if (touName) {
                        jsObj.put(StrUtil.toCamelCase(key), value);
                    } else {
                        jsObj.put(key, value);
                    }
                }
                resData.add(jsObj);
            }
        }
        return resData;
    }

    @Deprecated
    public Map<String, Object> save(CommonSaveData commonSaveData) {
        // 第一步: 查询表结构
        String table = commonSaveData.getTable();
        JSONArray data = commonSaveData.getData();
        JSONArray reArr = new JSONArray();
        if (null != data && data.size() > 0) {
            List<FieldInfo> page = DataBaseUtils.tableInfo(jdbcTemplate, table);
            // 需要添加的数据
            JSONArray addArr = new JSONArray();
            // 需要更新的数据
            JSONArray updateArr = new JSONArray();
            String pk = null;
            // 判断数据库是否有数据
            for (int i = 0; i < page.size(); i++) {
                FieldInfo js = page.get(i);
                String columnName = js.getField();
                String columnKey = js.getKey();
                if (columnKey.equals("PRI")) {
                    pk = columnName;
                    for (int j = 0; j < data.size(); j++) {
                        JSONObject mm = data.getJSONObject(j);
                        String id = mm.getStr(columnName);
                        if (WhyStringUtil.isEmpty(id)) {
                            addArr.add(mm);
                        } else {
                            updateArr.add(mm);
                        }
                    }
                    break;
                }
            }
            if (addArr.size() > 0) {
                for (int i = 0; i < addArr.size(); i++) {
                    JSONObject obj = addArr.getJSONObject(i);
                    insetData(table, obj, page);
                }
            }
            // 修改
            if (updateArr.size() > 0) {
                if (!WhyStringUtil.isEmpty(pk)) {
                    for (int i = 0; i < updateArr.size(); i++) {
                        String pkvalue = null;
                        StringBuffer sb = new StringBuffer();
                        StringBuffer set = new StringBuffer();
                        JSONObject obj = updateArr.getJSONObject(i);
                        List<String> list = new ArrayList<>();
                        sb.append("update " + table + " set ");
                        int tree = 0;
                        for (int j = 0; j < page.size(); j++) {
                            FieldInfo js = page.get(j);
                            String name = js.getField();
                            String dataType = js.getType();
                            String columnKey = js.getKey();
                            String cn = obj.getStr(name);
                            if ((!"datetime".equals(dataType) && null != cn) || (!WhyStringUtil.isEmpty(cn))) {
                                if (columnKey.equals("PRI")) {
                                    pkvalue = cn;
                                } else {
                                    if (set.length() > 0) {
                                        set.append(",`" + name + "` = ? ");
                                    } else {
                                        set.append("`" + name + "` = ? ");
                                    }
                                    if ("pid".equals(name) || "tid".equals(name) || "tree_code".equals(name)) {
                                        tree++;
                                    }
                                    list.add(cn);
                                }
                            }
                        }
                        sb.append(set);
                        sb.append(" where " + pk + " = ?");
                        if (null != pkvalue) {
                            list.add(pkvalue);
                            // 处理树
                            String oldTreecode = null;
                            String pid = obj.getStr("pid");
                            if (tree == 3) {
                                // 第一步：查询原数据
                                String sql = "select * from " + table + " where `" + pk + "` = ?";
                                List<Map<String, Object>> oldData = jdbcTemplate.queryForList(sql, pkvalue);
                                Map<String, Object> old = oldData.get(0);
                                String oldPid = old.get("pid").toString();
                                // 判断是否修改了父节点
                                if (!pid.equals(oldPid)) {
                                    oldTreecode = old.get("tree_code").toString();
                                    String parentTreeCode = "";
                                    // 第二步： 查找新的父节点数据
                                    List<Map<String, Object>> parentData = jdbcTemplate.queryForList("select * from " + table + " where `tid` = ?", pid);
                                    if (parentData.size() > 0) {
                                        Map<String, Object> parentObj = parentData.get(0);
                                        parentTreeCode = parentObj.get("tree_code").toString();
                                    }
                                    //获取新的 tree_code
                                    int l = parentTreeCode.length() + 3;
                                    String max = "select ifnull(max(t.treecode),0)+1 as treecode from " + table + " t where length(t.treecode) = ? and t.treecode LIKE ? ";
                                    List<Map<String, Object>> setcode = jdbcTemplate.queryForList(max, l, parentTreeCode + "%");
                                    int trint = 1;
                                    if (setcode.size() > 0) {
                                        JSONObject js = new JSONObject(setcode.get(0));
                                        trint = js.getInt("tree_code");
                                    }
                                    String tr = WhyStringUtil.createStrLength(trint + "", 3, "0");
                                    obj.put("tree_code", parentTreeCode + tr);
                                }
                            }
                            jdbcTemplate.update(sb.toString(), list.toArray());
                            if (oldTreecode != null) {
                                // 更新数据下的 treecode
                                String tr = obj.getStr("tree_code");
                                String updateTree = "update t_power t set t.treecode = concat( ? ,substring(t.treecode, ? )) where t.treecode like ? ";
                                jdbcTemplate.update(updateTree, tr, oldTreecode.length() + 1, oldTreecode + "%");
                            }
                        }
                    }
                }
            }
            reArr.addAll(addArr);
            reArr.addAll(updateArr);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "ok");
        map.put("data", reArr);
        return map;
    }

    @Override
    public BaseResult<Object> del(String code, JSONObject json) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from c_sql c where c.tid = ?", code);
        if (sqlRowSet.next()) {
            String mainTable = sqlRowSet.getString("main_table");
            String mainId = sqlRowSet.getString("main_id");
            String delSql = sqlRowSet.getString("del_sql").replaceAll("\n", "");
            String mainIdData = json.getStr(mainId);
            if (StrUtil.isNotBlank(mainIdData)) {
                return delData(delSql, json);
            } else {
                WebException.fail("没有查到数据,表:" + mainTable + ", ID:" + mainId);
            }
        }
        return BaseResult.fail();
    }

    private BaseResult<Object> delData(String delSql, JSONObject json) {
        BaseResult<Object> rest = new BaseResult<>();
        String sql = SqlUtils.delExplainSql(delSql, json);
        String[] strArr = SqlUtils.saveExplainData(delSql, json);
        log.info("============================================");
        log.info("执行删除语句: {} , {}", sql, json);
        log.info("============================================");
        int update = jdbcTemplate.update(sql, strArr);
        if (update > 0) {
            rest.setCode(WebErrCodeEnum.WEB_SUCCESS.getCode());
            rest.setMsg("删除成功");
        } else {
            rest.setCode(WebErrCodeEnum.WEB_ERR.getCode());
            rest.setMsg("删除失败");
        }
        return rest;
    }

    /**
     *
     */
    private void insetData(String table, JSONObject obj, List<FieldInfo> page) {
        StringBuffer datakey = new StringBuffer();
        StringBuffer set = new StringBuffer();
        List<String> list = new ArrayList<>();
        int tree = 0;
        int index = -1;
        for (int j = 0; j < page.size(); j++) {
            FieldInfo js = page.get(j);
            String name = js.getField();
            String dataType = js.getType();
            String cn = obj.getStr(name);
            // 初始化数据
            if (WhyStringUtil.isEmpty(cn) && name.equals("tid")) {
                cn = WhyStringUtil.getUUID();
            } else if (WhyStringUtil.isEmpty(cn) && name.equals("create_time")) {
                cn = DateUtil.now();
            } else if (WhyStringUtil.isEmpty(cn) && name.equals("del")) {
                cn = "0";
            }
            if ((!"datetime".equals(dataType) && null != cn) || "tree_code".equals(name) || (!WhyStringUtil.isEmpty(cn))) {
                if (datakey.length() > 0) {
                    datakey.append(",`" + name + "`");
                } else {
                    datakey.append("`" + name + "`");
                }
                if (set.length() > 0) {
                    set.append(",?");
                } else {
                    set.append("?");
                }
                if ("pid".equals(name) || "tid".equals(name) || "tree_code".equals(name)) {
                    tree++;
                }
                if ("tree_code".equals(name)) {
                    index = list.size();
                }
                list.add(cn);
            }
        }

        //=======================  如果是树
        if (tree == 3 && index != -1) {
            String pid = obj.getStr("pid");
            if (null != pid) {
                String parentTreeCode = "";
                // 第二步： 查找新的父节点数据
                List<Map<String, Object>> parentData = jdbcTemplate.queryForList("select * from " + table + " where `tid` = ?", pid);
                if (parentData.size() > 0) {
                    Map<String, Object> parentObj = parentData.get(0);
                    parentTreeCode = parentObj.get("tree_code").toString();
                }
                //获取新的 tree_code
                int l = parentTreeCode.length() + 3;
                String max = "select ifnull(max(t.treecode),0)+1 as treecode from " + table + " t where length(t.treecode) = ? and t.treecode LIKE ? ";
                List<Map<String, Object>> setcode = jdbcTemplate.queryForList(max, l, parentTreeCode + "%");
                int trint = 1;
                if (setcode.size() > 0) {
                    JSONObject js = new JSONObject(setcode.get(0));
                    trint = js.getInt("tree_code");
                }
                String tr = WhyStringUtil.createStrLength(trint + "", 3, "0");
                obj.put("tree_code", parentTreeCode + tr);
                list.set(index, parentTreeCode + tr);
            }
        }
        String sqlInsert = "insert into " + table + "(" + datakey.toString() + ") values (" + set.toString() + ")";
        jdbcTemplate.update(sqlInsert, list.toArray());
    }


    /**
     * 通用保存
     * 第一步: 查询主表是否有数据
     * 第二步: 没有数据, 执行插入语句
     * <p>
     * 第二步: 有数据, 执行更新语句
     *
     * @param code
     * @param json
     * @return
     */
    @Override
    public BaseResult save(String code, JSONObject json) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from c_sql c where c.tid = ?", code);
        BaseResult rest = new BaseResult();
        if (sqlRowSet.next()) {
            String mainTable = sqlRowSet.getString("main_table");
            String mainId = sqlRowSet.getString("main_id");
            String insetSql = sqlRowSet.getString("inset_sql").replaceAll("\n", "");
            String updateSql = sqlRowSet.getString("update_sql").replaceAll("\n", "");
            // 第一步: 查询主表是否有数据
            String mainIdData = json.getStr(mainId);
            if (StrUtil.isBlank(mainIdData)) {
                return insertData(insetSql, json);
            } else {

                List<JSONObject> tableData = execSql("select * from " + mainTable + " c where c." + mainId + " = ?", true, new String[]{mainIdData});
                if (tableData.size() > 1) {
                    WebException.fail("查询到有多条件数据,表:" + mainTable + ", ID:" + mainId);
                } else if (tableData.size() == 1) {
                    JSONObject oldData = tableData.get(0);
                    return updateData(updateSql, json, oldData);
                } else {
                    return insertData(insetSql, json);
                }
            }
        } else {
            rest.setCode(WebErrCodeEnum.WEB_ERR.getCode());
            rest.setMsg("找不到可用条件");
        }
        return rest;
    }

    /**
     * inset into aaa (a,b,c) values ([?],[?],[?])
     * 插入数据
     */
    private BaseResult insertData(String insertSql, JSONObject json) {
        BaseResult rest = new BaseResult();
        String sql = SqlUtils.saveExplainSql(insertSql);
        String[] strArr = SqlUtils.saveExplainData(insertSql, json);
        log.info("============================================");
        log.info("执行更新语句: {} , {}", sql, json);
        log.info("============================================");
        int update = jdbcTemplate.update(sql, strArr);
        if (update > 0) {
            rest.setCode(WebErrCodeEnum.WEB_SUCCESS.getCode());
            rest.setMsg("保存成功");
        } else {
            rest.setCode(WebErrCodeEnum.WEB_ERR.getCode());
            rest.setMsg("保存失败");
        }
        return rest;
    }

    /**
     * update tabele set aaa=bbb, ccc=ddd
     * 更新数据
     */
    private BaseResult updateData(String updateSql, JSONObject newData, JSONObject oldData) {
        BaseResult rest = new BaseResult();
        //=====
        String sql = SqlUtils.saveExplainSql(updateSql);
        String[] strArr = SqlUtils.updateExplainData(updateSql, newData, oldData);
        //=====
        log.info("============================================");
        log.info("执行保存语句: {} , {}", sql, newData);
        log.info("============================================");
        int update = jdbcTemplate.update(sql, strArr);
        if (update > 0) {
            rest.setCode(WebErrCodeEnum.WEB_SUCCESS.getCode());
            rest.setMsg("保存成功");
        } else {
            rest.setCode(WebErrCodeEnum.WEB_ERR.getCode());
            rest.setMsg("保存失败");
        }
        return rest;
    }

}
