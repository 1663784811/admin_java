package com.cyyaw.sql.buildcode.extend.mybatis;

import com.cyyaw.sql.buildcode.croe.entity.java.JavaColumn;
import com.cyyaw.sql.buildcode.croe.entity.java.JavaData;
import com.cyyaw.sql.buildcode.croe.tools.OperationTools;
import com.cyyaw.sql.buildcode.extend.CodeTemplate;

import java.util.List;

/**
 * TypeScript 类型
 */
public class TypeScriptType  implements CodeTemplate {

    private String table;
    private JavaColumn primaryKey;
    private List<JavaColumn> javaColumns;
    private String dbfiel = "";
    private String dbfiel_ = "";
    private String dbfiel_s = "";

    public TypeScriptType(JavaData javaData ){
        this.setJavaData(javaData);
    }
    @Override
    public void setJavaData(JavaData javaData){
        this.table = javaData.getTable();
        this.javaColumns = javaData.getJavaColumns();
        for (int i = 0; i < javaColumns.size(); i++) {
            JavaColumn javaColumn = javaColumns.get(i);
            Boolean isPrimary = javaColumn.getIsPrimary();
            if(isPrimary){
                primaryKey = javaColumn;
            }
        }
    }

    /**
     * 字段列表
     */
    public String getFieldList(){
        String str="";
        for (int i = 0; i < javaColumns.size(); i++) {
            JavaColumn javaColumn = javaColumns.get(i);
            String type = javaColumn.getJavaField();
            str+= "\t"+type+":"+ OperationTools.getToStypeType(javaColumn.getJavaType())+"     // "+javaColumn.getNote()+"\n";
        }
        return str;
    }

    /**
     * 默认数据
     */
    public String getDefaultData(){
        String str="";
        for (int i = 0; i < javaColumns.size(); i++) {
            JavaColumn javaColumn = javaColumns.get(i);
            String type = javaColumn.getJavaField();
            if(i==javaColumns.size()-1){
                str+= "\t"+type+":"+OperationTools.getToStypeDefaultData(javaColumn.getJavaType())+"     // "+javaColumn.getNote()+"\n";
            }else{
                str+= "\t"+type+":"+OperationTools.getToStypeDefaultData(javaColumn.getJavaType())+","+"     // "+javaColumn.getNote()+"\n";
            }
        }
        return str;
    }


    /**
     * js 查询参数
     */
    public String getParameter(){
        String str = "";
        for (int i = 0; i < javaColumns.size(); i++) {
            JavaColumn javaColumn = javaColumns.get(i);
            String type = javaColumn.getJavaField();
            String field = javaColumn.getColumnName();
            str += "    "+type+"EQ: {operator: \"=\", field: \""+field+"\", value: \"\", alias: \"t\", dataType: \""+OperationTools.getToStypeDefaultData(javaColumn.getJavaType())+"\"},\n";
            str += "    "+type+"LK: {operator: \"like\", field: \""+field+"\", value: \"\", alias: \"t\", dataType: \"string\"},\n";
            str += "    "+type+"GT: {operator: \">=\", field: \""+field+"\", value: \"\", alias: \"t\", dataType: \"number\"},\n";
            str += "    "+type+"LT: {operator: \"<=\", field: \""+field+"\", value: \"\", alias: \"t\", dataType: \"number\"},\n";
        }
        return str;
    }



}
