package com.cyyaw.sql.buildcode.extend.mybatis;


import com.cyyaw.sql.buildcode.croe.entity.java.JavaColumn;
import com.cyyaw.sql.buildcode.croe.entity.java.JavaData;
import com.cyyaw.sql.buildcode.croe.tools.OperationTools;
import com.cyyaw.sql.buildcode.extend.CodeTemplate;

import java.util.List;

/**
 * mybatis xml 插件
 */
public class MybatisXmlMapper implements CodeTemplate {
    //==================================================

    private String xmlPackage = "com.cf.ucenter.domain";


    //==================================================
    private String table;
    private JavaColumn primaryKey;
    private List<JavaColumn> javaColumns;

    private String dbfiel = "";
    private String dbfiel_ = "";
    private String dbfiel_s = "";

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
            if(i==0){
                dbfiel+=javaColumn.getColumnName();
                dbfiel_="#{"+ OperationTools.indexToLowerCase(javaColumn.getColumnName())+",jdbcType="+javaColumn.getMybatisType()+"}";
                dbfiel_s= javaColumn.getColumnName()+" = #{record."+OperationTools.indexToLowerCase(javaColumn.getColumnName())+",jdbcType="+javaColumn.getMybatisType()+"}";
            }else{
                dbfiel+=","+javaColumn.getColumnName();
                dbfiel_+=",#{"+OperationTools.indexToLowerCase(javaColumn.getColumnName())+",jdbcType="+javaColumn.getMybatisType()+"}";
                dbfiel_s+=","+javaColumn.getColumnName()+" = #{record."+OperationTools.indexToLowerCase(javaColumn.getColumnName())+",jdbcType="+javaColumn.getMybatisType()+"}";
            }
        }
    }

    public MybatisXmlMapper(JavaData javaData){
        this.setJavaData(javaData);
    }

    public String getBaseResultMap(){
        String xml = "<resultMap id=\"BaseResultMap\" type=\""+xmlPackage+"."+OperationTools.indexToUpperCase(table)+"\" >\n";
        for (int i = 0; i < javaColumns.size(); i++) {
            JavaColumn c = javaColumns.get(i);
            if(c.getIsPrimary()){
               xml+= "        <id column=\""+c.getColumnName()+"\" property=\""+ OperationTools.delSpecial(c.getColumnName())+"\" jdbcType=\""+c.getMybatisType()+"\" />\n";
            }else{
                xml+= "        <result column=\""+c.getColumnName()+"\" property=\""+OperationTools.delSpecial(c.getColumnName())+"\" jdbcType=\""+c.getMybatisType()+"\" />\n";
            }
        }
        xml+= "  </resultMap>";
        return xml;
    }


    public String getExampleWhereClause(){
        String xml = "  <sql id=\"Example_Where_Clause\" >\n" +
                "    <where >\n" +
                "      <foreach collection=\"oredCriteria\" item=\"criteria\" separator=\"or\" >\n" +
                "        <if test=\"criteria.valid\" >\n" +
                "          <trim prefix=\"(\" suffix=\")\" prefixOverrides=\"and\" >\n" +
                "            <foreach collection=\"criteria.criteria\" item=\"criterion\" >\n" +
                "              <choose >\n" +
                "                <when test=\"criterion.noValue\" >\n" +
                "                  and ${criterion.condition}\n" +
                "                </when>\n" +
                "                <when test=\"criterion.singleValue\" >\n" +
                "                  and ${criterion.condition} #{criterion.value}\n" +
                "                </when>\n" +
                "                <when test=\"criterion.betweenValue\" >\n" +
                "                  and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n" +
                "                </when>\n" +
                "                <when test=\"criterion.listValue\" >\n" +
                "                  and ${criterion.condition}\n" +
                "                  <foreach collection=\"criterion.value\" item=\"listItem\" open=\"(\" close=\")\" separator=\",\" >\n" +
                "                    #{listItem}\n" +
                "                  </foreach>\n" +
                "                </when>\n" +
                "              </choose>\n" +
                "            </foreach>\n" +
                "          </trim>\n" +
                "        </if>\n" +
                "      </foreach>\n" +
                "    </where>\n" +
                "  </sql>";
        return xml;
    }


    public String getUpdateByExampleWhereClause(){
        String xml = "  <sql id=\"Update_By_Example_Where_Clause\" >\n" +
                "    <where >\n" +
                "      <foreach collection=\"example.oredCriteria\" item=\"criteria\" separator=\"or\" >\n" +
                "        <if test=\"criteria.valid\" >\n" +
                "          <trim prefix=\"(\" suffix=\")\" prefixOverrides=\"and\" >\n" +
                "            <foreach collection=\"criteria.criteria\" item=\"criterion\" >\n" +
                "              <choose >\n" +
                "                <when test=\"criterion.noValue\" >\n" +
                "                  and ${criterion.condition}\n" +
                "                </when>\n" +
                "                <when test=\"criterion.singleValue\" >\n" +
                "                  and ${criterion.condition} #{criterion.value}\n" +
                "                </when>\n" +
                "                <when test=\"criterion.betweenValue\" >\n" +
                "                  and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n" +
                "                </when>\n" +
                "                <when test=\"criterion.listValue\" >\n" +
                "                  and ${criterion.condition}\n" +
                "                  <foreach collection=\"criterion.value\" item=\"listItem\" open=\"(\" close=\")\" separator=\",\" >\n" +
                "                    #{listItem}\n" +
                "                  </foreach>\n" +
                "                </when>\n" +
                "              </choose>\n" +
                "            </foreach>\n" +
                "          </trim>\n" +
                "        </if>\n" +
                "      </foreach>\n" +
                "    </where>\n" +
                "  </sql>";
        return xml;
    }


    public String getBaseColumnList(){
        String xml ="  <sql id=\"Base_Column_List\" >\n";
        for (int i = 0; i < javaColumns.size(); i++) {
            JavaColumn c = javaColumns.get(i);
            if(i==0){
                xml+=c.getColumnName();
            }else{
                xml+=","+c.getColumnName();
            }
        }
        xml+="\n  </sql>";
        return xml;
    }


    public String getSelectByExample(){
        String xml = "  <select id=\"selectByExample\" resultMap=\"BaseResultMap\" parameterType=\""+xmlPackage+"."+OperationTools.indexToUpperCase(table)+"Example\" >\n" +
                "    select\n" +
                "    <if test=\"distinct\" >\n" +
                "      distinct\n" +
                "    </if>\n" +
                "    <include refid=\"Base_Column_List\" />\n" +
                "        from "+table+"\n" +
                "    <if test=\"_parameter != null\" >\n" +
                "      <include refid=\"Example_Where_Clause\" />\n" +
                "    </if>\n" +
                "    <if test=\"orderByClause != null\" >\n" +
                "      order by ${orderByClause}\n" +
                "    </if>\n" +
                "  </select>";
        return xml;
    }

    public String getSelectByPrimaryKey(){
        String xml = "  <select id=\"selectByPrimaryKey\" resultMap=\"BaseResultMap\" parameterType=\"java.lang."+primaryKey.getJavaType()+"\" >\n" +
                "    select\n" +
                "    <include refid=\"Base_Column_List\" />\n" +
                "    from "+table+"\n" +
                "    where "+primaryKey.getColumnName()+" = #{"+primaryKey.getColumnName()+",jdbcType="+primaryKey.getMybatisType()+"}\n" +
                "  </select>";
        return xml;
    }


    public String getDeleteByPrimaryKey(){
        String xml = "  <delete id=\"deleteByPrimaryKey\" parameterType=\"java.lang."+primaryKey.getJavaType()+"\" >\n" +
                "    delete from "+table+"\n" +
                "    where "+primaryKey.getColumnName()+" = #{"+primaryKey.getColumnName()+",jdbcType="+primaryKey.getMybatisType()+"}\n" +
                "  </delete>";
        return xml;
    }

    public String getDeleteByExample(){
        String xml = "  <delete id=\"deleteByExample\" parameterType=\""+xmlPackage+"."+OperationTools.indexToUpperCase(table)+"Example\" >\n" +
                "    delete from "+table+"\n" +
                "    <if test=\"_parameter != null\" >\n" +
                "      <include refid=\"Example_Where_Clause\" />\n" +
                "    </if>\n" +
                "  </delete>";
        return xml;
    }

    public String getInsert(){
        String xml = "  <insert id=\"insert\" parameterType=\""+xmlPackage+"."+OperationTools.indexToUpperCase(table)+"\" >\n" +
                "    insert into "+table+" ("+dbfiel+")\n" +
                "    values (\n" +
                dbfiel_+
                "        )\n" +
                "  </insert>";
        return xml;
    }


    public String getInsertSelective(){
        String xml = "  <insert id=\"insertSelective\" parameterType=\""+xmlPackage+"."+OperationTools.indexToUpperCase(table)+"\" >\n" +
                "    insert into "+table+"\n" +
                "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n";
        for (int i = 0; i < javaColumns.size(); i++) {
            JavaColumn javaColumn = javaColumns.get(i);
            xml += "      <if test=\""+OperationTools.indexToLowerCase(javaColumn.getColumnName())+" != null\" >\n" +
                    "        "+javaColumn.getColumnName()+",\n" +
                    "      </if>\n";
        }
        xml+="    </trim>\n";
        xml+="    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\n";
        for (int i = 0; i < javaColumns.size(); i++) {
            JavaColumn javaColumn = javaColumns.get(i);
            xml +=  "      <if test=\""+OperationTools.indexToLowerCase(javaColumn.getColumnName())+" != null\" >\n" +
                    "        #{"+OperationTools.indexToLowerCase(javaColumn.getColumnName())+",jdbcType="+javaColumn.getMybatisType()+"},\n" +
                    "      </if>\n";
        }
        xml += "    </trim>\n" +
                "  </insert>";
        return xml;
    }


    public String getCountByExample(){
        String xml = "  <select id=\"countByExample\" parameterType=\""+xmlPackage+"."+OperationTools.indexToUpperCase(table)+"Example\" resultType=\"java.lang.Integer\" >\n" +
                "    select count(*) from "+table+"\n" +
                "    <if test=\"_parameter != null\" >\n" +
                "      <include refid=\"Example_Where_Clause\" />\n" +
                "    </if>\n" +
                "  </select>";
        return xml;
    }


    public String getUpdateByExampleSelective(){
        String xml = "  <update id=\"updateByExampleSelective\" parameterType=\"map\" >\n" +
                "    update "+table+"\n" +
                "    <set >\n";

        for (int i = 0; i <javaColumns.size() ; i++) {
            JavaColumn javaColumn = javaColumns.get(i);
            xml+= "      <if test=\"record."+OperationTools.indexToLowerCase(javaColumn.getColumnName())+" != null\" >\n" +
                    "        "+javaColumn.getColumnName()+" = #{record."+OperationTools.indexToLowerCase(javaColumn.getColumnName())+",jdbcType="+javaColumn.getMybatisType()+"},\n" +
                    "      </if>\n" ;

        }
        xml+=   "    </set>\n" +
                "    <if test=\"_parameter != null\" >\n" +
                "      <include refid=\"Update_By_Example_Where_Clause\" />\n" +
                "    </if>\n" +
                "  </update>";
        return xml;
    }

    public String getUpdateByExample(){
        String xml = "  <update id=\"updateByExample\" parameterType=\"map\" >\n" +
                "    update "+table+"\n" +
                "    set\n" +
                "        "+dbfiel_s+"\n" +
                "    <if test=\"_parameter != null\" >\n" +
                "      <include refid=\"Update_By_Example_Where_Clause\" />\n" +
                "    </if>\n" +
                "  </update>";
        return xml;
    }

    public String getUpdateByPrimaryKeySelective(){
      String xml = "  <update id=\"updateByPrimaryKeySelective\" parameterType=\""+xmlPackage+"."+OperationTools.indexToUpperCase(table)+"\" >\n" +
               "    update "+table+"\n" +
               "    <set >\n";
        for (int i = 0; i < javaColumns.size(); i++) {
            JavaColumn javaColumn = javaColumns.get(i);
            if(!javaColumn.getIsPrimary()){
                xml += "      <if test=\""+OperationTools.indexToLowerCase(javaColumn.getColumnName())+" != null\" >\n" +
                        "        "+javaColumn.getColumnName()+" = #{"+OperationTools.indexToLowerCase(javaColumn.getColumnName())+",jdbcType="+javaColumn.getMybatisType()+"},\n" +
                        "      </if>\n";
            }
        }
               xml +="    </set>\n"+
                "    where  id = #{id,jdbcType=VARCHAR}\n" +
               "  </update>";
      return xml;
    }


    public String getUpdateByPrimaryKey(){
        String xml = "  <update id=\"updateByPrimaryKey\" parameterType=\""+xmlPackage+"."+OperationTools.indexToUpperCase(table)+"\" >\n" +
                "    update "+table+"\n" +
                "    set \n";

        for (int i = 0; i < javaColumns.size(); i++) {
            JavaColumn javaColumn = javaColumns.get(i);
            if(!javaColumn.getIsPrimary()){
                if(i+1==javaColumns.size()){
                    xml+="        "+javaColumn.getColumnName()+" = #{"+OperationTools.indexToLowerCase(javaColumn.getColumnName())+",jdbcType="+javaColumn.getMybatisType()+"}\n";
                }else{
                    xml+="        "+javaColumn.getColumnName()+" = #{"+OperationTools.indexToLowerCase(javaColumn.getColumnName())+",jdbcType="+javaColumn.getMybatisType()+"},\n";
                }

            }
        }
               xml+= "    where  "+primaryKey.getColumnName()+" = #{"+OperationTools.indexToLowerCase(primaryKey.getColumnName())+",jdbcType="+primaryKey.getMybatisType()+"}\n" +
                "  </update>";
        return xml;
    }


    public String getSelectByQuery(){
        String xml = "  <select id=\"selectByQuery\" parameterType=\""+xmlPackage+".request."+OperationTools.indexToUpperCase(table)+"Query\" resultMap=\"BaseResultMap\">\n";
               xml += "    select \n";
               xml += "   <include refid=\"Base_Column_List\" /> \n";
               xml += "    from "+ table+"\n";
               //==============================
                xml+= "    <trim prefix=\"where \" prefixOverrides=\"and\" suffixOverrides=\",\" >\n";
                for (int i = 0; i < javaColumns.size(); i++) {
                    JavaColumn javaColumn = javaColumns.get(i);
                    String type = javaColumn.getJavaType();
                    if("String".equals(type)){
                        xml+="      <if test=\""+OperationTools.indexToLowerCase(javaColumn.getColumnName())+" != null and "+OperationTools.indexToLowerCase(javaColumn.getColumnName())+" != ''\" >\n";
                        xml+="        "+javaColumn.getColumnName()+" = #{"+OperationTools.indexToLowerCase(javaColumn.getColumnName())+",jdbcType="+javaColumn.getMybatisType()+"},\n";
                        xml+="      </if>\n";
                    }else{
                        xml+="      <if test=\""+OperationTools.indexToLowerCase(javaColumn.getColumnName())+" != null\" >\n";
                        xml+="        "+javaColumn.getColumnName()+" = #{"+OperationTools.indexToLowerCase(javaColumn.getColumnName())+",jdbcType="+javaColumn.getMybatisType()+"},\n";
                        xml+="      </if>\n";
                    }
                }
                xml+= "    </trim>\n";
               //==============================
               xml+= "    <if test=\"orderBy != null and orderBy != ''\" >\n";
               xml+= "      order by #{orderBy,jdbcType=VARCHAR}\n";
               xml+= "        <if test=\"sort != null and sort !=''\" >\n";
               xml+= "         #{sort,jdbcType=VARCHAR}\n";
               xml+= "        </if>\n";
               xml+= "    </if>\n";
               xml+= "    <if test=\"page != null and size != null\" >\n";
               xml+= "      limit ${(page-1)*size},${size}\n";
               xml+= "    </if>\n";
               xml += "  </select>\n";
        return xml;
    }

}
