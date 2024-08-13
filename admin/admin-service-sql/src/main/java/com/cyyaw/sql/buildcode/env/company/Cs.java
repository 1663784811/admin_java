package com.cyyaw.sql.buildcode.env.company;


import com.cyyaw.sql.buildcode.croe.entity.java.JavaColumn;
import com.cyyaw.sql.buildcode.croe.entity.java.JavaData;
import com.cyyaw.sql.buildcode.extend.CodeTemplate;

import java.util.List;

public class Cs implements CodeTemplate {

    private String table;
    private JavaColumn primaryKey;
    private List<JavaColumn> javaColumns;
    private String dbfiel = "";
    private String dbfiel_ = "";
    private String dbfiel_s = "";

    public Cs(JavaData javaData){
        this.setJavaData(javaData);
    }

    @Override
    public void setJavaData(JavaData javaData) {
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


    public String getAllowFileds(){
        String str="";
        for (int i = 0; i < javaColumns.size(); i++) {
            JavaColumn javaColumn = javaColumns.get(i);
            String col = javaColumn.getColumnName();
            str+= "        allowFileds.add(\""+ col +"\");\n";
        }
        return str;
    }

    public String getAllowFiledsMap(){
        String str="";
        for (int i = 0; i < javaColumns.size(); i++) {
            JavaColumn javaColumn = javaColumns.get(i);
            String col = javaColumn.getColumnName();
            str+= "        allowFiledsMap.put(\""+col+"\",\"t\");\n";
        }
        return str;
    }






}
