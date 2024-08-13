package com.cyyaw.jpa.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.cyyaw.jpa.util.entity.FieldInfo;
import com.cyyaw.jpa.util.entity.TableInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

/**
 * 数据库
 */
public class DataBaseUtils {

    /**
     * 获取 字段 信息
     */
    public static List<FieldInfo> tableInfo(JdbcTemplate jt, String table) {
        List<Map<String, Object>> maps = jt.queryForList("SHOW FULL COLUMNS FROM " + table);
        List<FieldInfo> arr = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {
            Map<String, Object> oldMap = maps.get(i);
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setField(StrUtil.toString(oldMap.get("Field")));
            fieldInfo.setType(StrUtil.toString(oldMap.get("Type")));
            fieldInfo.setCollation(StrUtil.toString(oldMap.get("Collation")));
            fieldInfo.setIsNull(StrUtil.toString(oldMap.get("Null")));
            fieldInfo.setKey(StrUtil.toString(oldMap.get("Kield")));
            fieldInfo.setDefaultValue(StrUtil.toString(oldMap.get("Default")));
            fieldInfo.setExtra(StrUtil.toString(oldMap.get("Extra")));
            arr.add(fieldInfo);
        }
        return arr;
    }


    /**
     * 创建inset 语句
     *
     * @param tableInfo
     * @return
     */
    public static String createInsetSql(TableInfo tableInfo) {
        String table = tableInfo.getTable();
        List<FieldInfo> fieldInfoList = tableInfo.getFieldInfoList();
        StringBuffer sb = new StringBuffer();
        StringBuffer fl = new StringBuffer();
        for (int i = 0; i < fieldInfoList.size(); i++) {
            FieldInfo fieldInfo = fieldInfoList.get(i);
            String extra = fieldInfo.getExtra();
            if (extra.indexOf("auto_increment") == -1) {
                String field = fieldInfo.getField();
                if (sb.length() > 0) {
                    fl.append("," + field);
                    sb.append(",?");
                } else {
                    fl.append(field);
                    sb.append("?");
                }
            }
        }
        return "insert into " + table + "(" + fl.toString() + ") VALUES (" + sb.toString() + ")";
    }


    /**
     * 创建  随机 数据
     *
     * @return
     */
    public static Object[] createData(List<FieldInfo> fieldInfoList) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < fieldInfoList.size(); i++) {
            FieldInfo fieldInfo = fieldInfoList.get(i);
            String extra = fieldInfo.getExtra();
            if (extra.indexOf("auto_increment") == -1) {
                String type = fieldInfo.getType();
                if (type.indexOf("int") != -1) {
                    list.add(RandomUtil.randomInt());
                } else if (type.indexOf("datetime") != -1) {
                    list.add(DateUtil.now());
                } else if (type.indexOf("date") != -1) {
                    list.add(DateUtil.date().toString("yyyy-MM-dd"));
                } else if (type.indexOf("decimal") != -1) {
                    list.add(RandomUtil.randomInt());
                } else {
                    // varchar()
                    Integer length = 10;
                    if (type.indexOf("varchar(") != -1) {
                        Integer integer = Integer.valueOf(type.substring("varchar(".length(), type.indexOf(")")));
                        length = (int)Math.floor(integer * 0.75);
                    }
                    list.add(RandomUtil.randomString(length));
                }
            }
        }
        return list.toArray();
    }


}
