package com.cyyaw.sql.buildcode.env.controller;

import com.cyyaw.sql.buildcode.croe.code.CreateCode;
import com.cyyaw.sql.buildcode.croe.code.impl.CreateCodeFreemarker;
import com.cyyaw.sql.buildcode.croe.database.DataBase;
import com.cyyaw.sql.buildcode.croe.entity.java.JavaColumn;
import com.cyyaw.sql.buildcode.croe.entity.java.JavaData;
import com.cyyaw.sql.buildcode.croe.tools.InterfaceToos;
import com.cyyaw.sql.buildcode.croe.tools.OperationTools;
import com.cyyaw.sql.buildcode.croe.tools.TypeTools;
import com.cyyaw.sql.buildcode.env.company.Cs;
import com.cyyaw.sql.buildcode.extend.controller.TxVue;
import com.cyyaw.sql.buildcode.extend.mybatis.MybatisXmlMapper;
import com.cyyaw.sql.buildcode.extend.mybatis.TkJavaEntity;
import com.cyyaw.sql.buildcode.extend.mybatis.TypeScriptType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class CodeController {


    public void buildCode(String url, String user, String pwd, String table) throws SQLException, ClassNotFoundException, IOException {
        url = url + "?user=" + user + "&password=" + pwd + "&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        DataBase dataBase = new DataBase("com.mysql.cj.jdbc.Driver", url, user, pwd);


        //
        List<JavaData> tableList = dataBase.getTableList(table);


        // 构建代码
        createCode(tableList);


    }

    public void createCode(List<JavaData> tableList) throws IOException {

        CreateCode createCode = new CreateCodeFreemarker(null,"./template", "./code");
        OperationTools operationTools = new OperationTools();
        InterfaceToos interfaceToos = new InterfaceToos();
        if (null != tableList) {
            for (int i = 0; i < tableList.size(); i++) {
                JavaData javaData = tableList.get(i);
                String st = javaData.getTable().toLowerCase();
                if( !"hibernate_sequence".equals(st)){
                    List<JavaColumn> javaColumns = javaData.getJavaColumns();
                    Map<String, Object> map = new HashMap();
                    //===========================================

                    //基础包
                    map.put("basePackage", "com.cyyaw.weixin.admin");
                    //基础路径
                    map.put("basePathVue", "");
                    //===========================================
                    //数据表信息
                    map.put("javaData", javaData);
                    //数据字段列表
                    map.put("javaColumns", javaColumns);
                    map.put("vueJsons", TypeTools.javaColumnList2VueJsonList(javaColumns));
                    //===========================================
                    //工具类
                    map.put("operationTools", operationTools);
                    //工具类
                    map.put("interfaceToos", interfaceToos);
                    // 表名首字母大写
                    map.put("__Table__", OperationTools.indexToUpperCase(javaData.getTable()));
                    // 表名首字母小写
                    map.put("__table__", OperationTools.indexToLowerCase(javaData.getTable()));
                    // 表注释
                    map.put("__table__note__", javaData.getTableNote());
                    // 表名所有字母小写了
                    map.put("__table__all__", OperationTools.allToLowerCase(javaData.getTable()));
                    // 主键首字母大写
                    map.put("__Pk__", OperationTools.indexToUpperCase(OperationTools.getPK(javaColumns).getColumnName()));
                    // 主键首字母小写
                    map.put("__pk__", OperationTools.indexToLowerCase(OperationTools.getPK(javaColumns).getColumnName()));
                    // 主键all母小写
                    map.put("__pk_all__", OperationTools.allToLowerCase(OperationTools.getPK(javaColumns).getColumnName()));
                    // 主键java首字母大写
                    map.put("__pkJava__", OperationTools.getPK(javaColumns).getJavaType());


                    //===========================================  扩展

                    map.put("tkJavaEntity", new TkJavaEntity(javaData));


                    map.put("txVue", new TxVue(javaData));


                    map.put("mybatisXml", new MybatisXmlMapper(javaData));
                    map.put("typeScriptType", new TypeScriptType(javaData));
                    map.put("cs", new Cs(javaData));

                    //===========================================  常用变量
                    createCode.setDataMap(map);
                    System.out.println("正在生成文件。。。:" + tableList.get(i).getTableNote());
                    if (createCode.out()) {
                        System.out.println("生成文件成功");
                    } else {
                        System.out.println("生成文件失败");
                    }
                }
            }
        }
    }


}
