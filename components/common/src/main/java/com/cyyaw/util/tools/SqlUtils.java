package com.cyyaw.util.tools;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.excel.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解释sql工具类
 *
 * <prl>
 * <p>
 * select * from sys_user s where s.id = '123' and s.name = '张三'
 * {=a select * from sys_user s where [s.id] and [%s.name] {=b and [s.age]} }
 * <p>
 * {=a }  块判断
 * <p>
 * []  --->   =
 * [@]  --->   in
 * [!@] ---->  not in
 * [%]  ---->  like
 * [L%]  ---->  like
 * [R%]  ---->  like
 * [!%]  ---->  not like
 * [:=]  ---->  别名  [cc:=sss]
 * [!!>=] ---->  大于等于
 * [!!<=] ---->  小于等于
 *
 * </prl>
 */
public class SqlUtils {

    /**
     * 解释sql
     */
    public static String explainSql(String sql, JSONObject json) {
        Map<String, String> map = explainSquare(sql, json);
        for (String key : map.keySet()) {
            // 替换[]里的数据
            sql = sql.replace(key, map.get(key));
        }
        return sql;
    }

    /**
     * 解释内容{}括号
     */
    private static String explainCurly(String sql, String strArr[]) {
        int index = sql.indexOf("{=");
        if (index != -1) {
            for (int i = index; i < sql.length(); i++) {

            }
            return null;
        } else {
            return sql;
        }
    }

    /**
     * 解释 [] 括号内的内容
     *
     * @param sql
     * @return
     */
    private static Map<String, String> explainSquare(String sql, JSONObject json) {
        Map<String, String> map = new HashMap<>();
        int start = 0;
        int startFlag = 0;
        int endFlag = 0;
        for (int i = 0; i < sql.length(); i++) {
            if (sql.charAt(i) == '[') {
                startFlag++;
                if (startFlag == endFlag + 1) {
                    start = i;
                }
            } else if (sql.charAt(i) == ']') {
                endFlag++;
                if (endFlag == startFlag) {
                    map.put(sql.substring(start, i + 1), explainWhere(sql.substring(start + 1, i), json));
                }
            }
        }
        return map;
    }


    /**
     * []     ---->   =
     * [@]    ---->   in
     * [!@]   ---->  not in
     * [%]    ---->  like
     * [L%]   ---->  like
     * [R%]   ---->  like
     * [!%]   ---->  not like
     * [>=] ---->  大于等于
     * [<=] ---->  小于等于
     * [>] ---->  大于
     * [<] ---->  小于
     * [:=]   ---->  别名  [cc:=sss]
     * [*]   ----> null
     * [!*]   ----> 非null
     */
    /**
     * 解释 where 条件的内容
     *
     * @param str
     * @param json
     * @return
     */
    private static String explainWhere(String str, JSONObject json) {
        int index = 0;
        int keyIndex = 0;
        // 别名
        String key = null;
        // 是否可以传空
        boolean isValueNull = true;
        //=============================  第一层: 是否可以为空
        if (str.indexOf("&") == 0) {
            str = str.substring(1, str.length());
            isValueNull = false;
        }
        //=============================  第二层: 别名
        if ((keyIndex = str.indexOf(":=")) > 0) {
            key = str.substring(0, keyIndex);
            str = str.substring(keyIndex + 2, str.length());
        }
        // =============================   第三层：条件
        if ((index = str.indexOf("!@")) == 0) {
            String keyN = str.substring(index + 2);
            String value = null;
            if (null != key) {
                value = json.getStr(key);
            } else {
                String[] s = keyN.split("\\.");
                value = json.getStr(s[s.length - 1]);
            }
            if (!StringUtils.isEmpty(value)) {
                return keyN + " not in (" + addstr(value, ",", "'") + ")";
            }
        } else if ((index = str.indexOf("@")) == 0) {
            String keyN = str.substring(index + 1);
            String value = null;
            if (null != key) {
                value = json.getStr(key);
            } else {
                String[] s = keyN.split("\\.");
                value = json.getStr(s[s.length - 1]);
            }
            if (!StringUtils.isEmpty(value)) {
                return keyN + " in (" + addstr(value, ",", "'") + ")";
            }
        } else if ((index = str.indexOf("!%")) == 0) {
            String keyN = str.substring(index + 2);
            String value = null;
            if (null != key) {
                value = json.getStr(key);
            } else {
                String[] s = keyN.split("\\.");
                value = json.getStr(s[s.length - 1]);
            }
            if (!StringUtils.isEmpty(value)) {
                return keyN + " not like '" + value + "'";
            }
        } else if ((index = str.indexOf("L%")) == 0) {
            String keyN = str.substring(index + 2);
            String value = null;
            if (null != key) {
                value = json.getStr(key);
            } else {
                String[] s = keyN.split("\\.");
                value = json.getStr(s[s.length - 1]);
            }
            if (!StringUtils.isEmpty(value)) {
                return keyN + " like '%" + value + "'";
            }
        } else if ((index = str.indexOf("R%")) == 0) {
            String keyN = str.substring(index + 2);
            String value = null;
            if (null != key) {
                value = json.getStr(key);
            } else {
                String[] s = keyN.split("\\.");
                value = json.getStr(s[s.length - 1]);
            }
            if (!StringUtils.isEmpty(value)) {
                return keyN + " like '" + value + "%'";
            }
        } else if ((index = str.indexOf("%")) == 0) {
            String keyN = str.substring(index + 1);
            String value = null;
            if (null != key) {
                value = json.getStr(key);
            } else {
                String[] s = keyN.split("\\.");
                value = json.getStr(s[s.length - 1]);
            }
            if (!StringUtils.isEmpty(value)) {
                return keyN + " like '%" + value + "%'";
            }
        } else if ((index = str.indexOf(">=")) == 0) {
            String keyN = str.substring(index + 2);
            String value = null;
            if (null != key) {
                value = json.getStr(key);
            } else {
                String[] s = keyN.split("\\.");
                value = json.getStr(s[s.length - 1]);
            }
            if (!StringUtils.isEmpty(value)) {
                return keyN + " >= '" + value + "'";
            }
        } else if ((index = str.indexOf("<=")) == 0) {
            String keyN = str.substring(index + 2);
            String value = null;
            if (null != key) {
                value = json.getStr(key);
            } else {
                String[] s = keyN.split("\\.");
                value = json.getStr(s[s.length - 1]);
            }
            if (!StringUtils.isEmpty(value)) {
                return keyN + " <= '" + value + "'";
            }
        } else if ((index = str.indexOf(">")) == 0) {
            String keyN = str.substring(index + 1);
            String value = null;
            if (null != key) {
                value = json.getStr(key);
            } else {
                String[] s = keyN.split("\\.");
                value = json.getStr(s[s.length - 1]);
            }
            if (!StringUtils.isEmpty(value)) {
                return keyN + " > '" + value + "'";
            }
        } else if ((index = str.indexOf("<")) == 0) {
            String keyN = str.substring(index + 1);
            String value = null;
            if (null != key) {
                value = json.getStr(key);
            } else {
                String[] s = keyN.split("\\.");
                value = json.getStr(s[s.length - 1]);
            }
            if (!StringUtils.isEmpty(value)) {
                return keyN + " < '" + value + "'";
            }
        } else {
            String keyN = str;
            String value = null;
            if (null != key) {
                value = json.getStr(key);
            } else {
                String[] s = keyN.split("\\.");
                value = json.getStr(s[s.length - 1]);
            }
            if (!StringUtils.isEmpty(value)) {
                return keyN + " = '" + value + "'";
            }
        }
        // ============== 空值才会跑到最后
        if (!isValueNull) {
            return "1<>1";
        } else {
            return "1=1";
        }
    }

    /**
     * 添加字符串
     *
     * @param str
     * @param rstr
     * @param addstr
     * @return
     */
    private static String addstr(final String str, final String rstr, final String addstr) {
        if (!StringUtils.isEmpty(str)) {
            String[] split = str.split(rstr);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < split.length; i++) {
                if (i == 0 && split.length > 1) {
                    sb.append(addstr + split[i] + addstr);
                } else {
                    sb.append("," + addstr + split[i] + addstr);
                }

            }
            return sb.toString();
        } else {
            return null;
        }
    }


    /**
     * 通用保存， 解释数据
     *
     * @param sql
     * @param json
     *
     * 正常[]
     * 默认字符串[df:str_默认字符串]
     * 函数[df:fn_函数名]
     *      支持的函数名: uuid     (  32位 )
     *
     */
    public static String[] saveExplainData(String sql, JSONObject json) {
        List<String> rest = new ArrayList<>();
        int startIndex = 0;
        for (int i = 0; i < sql.length(); i++) {
            if (sql.charAt(i) == '[') {
                startIndex = i;
            } else if (sql.charAt(i) == ']') {
                StringBuffer jsonKey = new StringBuffer();
                String key = sql.substring(startIndex + 1, i);
                int lastInx = key.lastIndexOf(":");
                if (lastInx != -1) {
                    jsonKey.append(key.substring(lastInx + 1, key.length()));
                } else {
                    jsonKey.append(key);
                }
                String str = json.getStr(jsonKey.toString());
                if (StrUtil.isNotBlank(str)) {
                    rest.add(str);
                } else {
                    String dfStr = "df:";
                    int defIndex = key.indexOf(dfStr);
                    if (defIndex == 0) {
                        // 设置默认
                        String[] split = key.split(":");
                        if (split.length >= 2) {
                            String defType = split[1];
                            if (defType.indexOf("str_") == 0) {
                                rest.add(defType.substring("str_".length(), defType.length()));
                            } else if (defType.indexOf("fn_") == 0) {
                                String fnName = defType.substring("fn_".length(), defType.length());
                                if (StrUtil.isNotBlank(fnName)) {
                                    if ("uuid".equals(fnName)) {
                                        rest.add(WhyStringUtil.getUUID());
                                    } else {
                                        rest.add(fnName);
                                    }
                                } else {
                                    // 默认
                                    rest.add(defType);
                                }
                            } else {
                                // 默认
                                rest.add(defType);
                            }
                        } else {
                            // 默认
                            rest.add("");
                        }
                    } else {
                        // 默认
                        rest.add("");
                    }
                }
            }
        }
        return rest.toArray(new String[rest.size()]);
    }

    /**
     * 通用保存, 解释sql
     * 把 [] 号里的东西替换成 ? 号
     */
    public static String saveExplainSql(String sql) {
        StringBuffer sb = new StringBuffer();
        boolean start = false;
        for (int i = 0; i < sql.length(); i++) {
            if (sql.charAt(i) == '[') {
                start = true;
            } else if (sql.charAt(i) == ']') {
                start = false;
                sb.append("?");
            } else if (!start) {
                sb.append(sql.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 通用更新， 解释数据
     */
    public static String[] updateExplainData(String sql, JSONObject newData, JSONObject oldData) {
        List<String> rest = new ArrayList<>();
        int startIndex = 0;
        for (int i = 0; i < sql.length(); i++) {
            if (sql.charAt(i) == '[') {
                startIndex = i;
            } else if (sql.charAt(i) == ']') {
                StringBuffer jsonKey = new StringBuffer();
                String key = sql.substring(startIndex + 1, i);
                int lastInx = key.lastIndexOf(":");
                if (lastInx != -1) {
                    jsonKey.append(key.substring(lastInx + 1, key.length()));
                } else {
                    jsonKey.append(key);
                }
                String str = newData.getStr(jsonKey.toString());
                if (StrUtil.isNotBlank(str)) {
                    rest.add(str);
                } else {
                    String dfStr = "df:";
                    int defIndex = key.indexOf(dfStr);
                    if (defIndex == 0) {
                        // 设置默认
                        String[] split = key.split(":");
                        if (split.length >= 2) {
                            String defType = split[1];
                            if (defType.indexOf("str_") == 0) {
                                rest.add(defType.substring("str_".length(), defType.length()));
                            } else if (defType.indexOf("fn_") == 0) {
                                String fnName = defType.substring("fn_".length(), defType.length());
                                if (StrUtil.isNotBlank(fnName)) {
                                    if ("uuid".equals(fnName)) {
                                        rest.add(WhyStringUtil.getUUID());
                                    } else {
                                        rest.add(fnName);
                                    }
                                } else {
                                    // 默认
                                    rest.add(defType);
                                }
                            } else {
                                // 默认
                                rest.add(defType);
                            }
                        } else {
                            // 默认
                            rest.add("");
                        }
                    } else {
                        // 默认
                        rest.add("");
                    }
                }
            }
        }
        return rest.toArray(new String[rest.size()]);
    }

    /**
     * 通用删除, 解释sql
     * 把 [] 号里的东西替换成 ? 号
     */
    public static String delExplainSql(String sql, JSONObject json) {
        StringBuffer sb = new StringBuffer();
        boolean start = false;
        for (int i = 0; i < sql.length(); i++) {
            if (sql.charAt(i) == '[') {
                start = true;
            } else if (sql.charAt(i) == ']') {
                start = false;
                sb.append("?");
            } else if (!start) {
                sb.append(sql.charAt(i));
            }
        }
        return sb.toString();
    }

}
