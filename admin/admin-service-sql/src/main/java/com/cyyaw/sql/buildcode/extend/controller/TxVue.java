package com.cyyaw.sql.buildcode.extend.controller;


import com.cyyaw.sql.buildcode.croe.entity.java.JavaColumn;
import com.cyyaw.sql.buildcode.croe.entity.java.JavaData;

/**
 * @author why
 */
public class TxVue extends TxAbstract {
    public TxVue(JavaData javaData) {
        this.setJavaData(javaData);
    }

    /**
     * 搜索栏
     */
    public String getSearchItem() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < javaColumns.size(); i++) {
            JavaColumn javaColumn = javaColumns.get(i);
            String note = javaColumn.getNote();
            String javaField = javaColumn.getJavaField();
            sb.append("      <div class=\"searchItem\">\n" +
                    "        <div class=\"inputLabel\">" + note + "：</div>\n" +
                    "        <div class=\"inputBox\">\n" +
                    "          <el-input size=\"mini\" placeholder=\"" + note + "\"  v-model=\"listQuery.equals_" + javaField + "\"></el-input>\n" +
                    "        </div>\n" +
                    "      </div>\n");
        }
        return sb.toString();
    }

    /**
     * 表格列
     */
    public String getTableColumn() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < javaColumns.size(); i++) {
            JavaColumn javaColumn = javaColumns.get(i);
            String note = javaColumn.getNote();
            String javaField = javaColumn.getJavaField();
            String columnName = javaColumn.getColumnName();
            if (!javaColumn.getIsPrimary()) {
                sb.append("        <el-table-column label=\"" + note + "\" align=\"center\"  sortable=\"custom\" prop=\"" + columnName + "\" >\n" +
                        "          <template slot-scope=\"{row}\">\n" +
                        "            <span>{{ row." + javaField + "}}</span>\n" +
                        "          </template>\n" +
                        "        </el-table-column>\n");
            }
        }
        return sb.toString();
    }

    /**
     * 输入框
     */
    public String getFormItem() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < javaColumns.size(); i++) {
            JavaColumn javaColumn = javaColumns.get(i);
            String note = javaColumn.getNote();
            String javaField = javaColumn.getJavaField();
            if (!javaColumn.getIsPrimary()) {
                sb.append("        <el-form-item label=\"" + note + ":\">\n" +
                        "          <el-input size=\"mini\" v-model=\"tempParamData." + javaField + "\" placeholder=\"" + note + "\"/>\n" +
                        "        </el-form-item>\n");
            }
        }
        return sb.toString();
    }

}
