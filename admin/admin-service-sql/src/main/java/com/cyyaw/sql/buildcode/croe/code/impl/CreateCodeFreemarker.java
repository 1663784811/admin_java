package com.cyyaw.sql.buildcode.croe.code.impl;

import com.cyyaw.sql.buildcode.croe.code.CreateCode;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;


/**
 * 构建代码
 */
public class CreateCodeFreemarker implements CreateCode {

    private Configuration cfg;
    private File fileCreateCode;
    private Map<String, Object> dataMap;

    //========================================   配置START
    private String templatePath;//模板路径
    private String createFilePath;//输出文件路径
    //======================================== 配置END

    public CreateCodeFreemarker(Map<String, Object> dataMap, String templatePath, String createFilePath) throws IOException {
        init(dataMap, templatePath, createFilePath);
    }

    /**
     * 初始化
     *
     * @param dataMap
     * @param templatePath
     * @param createFilePath
     * @throws IOException
     */
    public void init(Map<String, Object> dataMap, String templatePath, String createFilePath) throws IOException {
        this.templatePath = templatePath;
        this.createFilePath = createFilePath;
        cfg = new Configuration(Configuration.VERSION_2_3_28);
        fileCreateCode = new File(templatePath);
        System.out.println(fileCreateCode.getAbsolutePath());
        //设置模板解释目录
        cfg.setDirectoryForTemplateLoading(fileCreateCode);
        //设置默认编码
        cfg.setDefaultEncoding("UTF-8");
        //设置出错解释方式
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        // ===创建数据模型
        this.dataMap = dataMap;
    }


    /**
     * 输出生成的文件
     *
     * @return
     */
    public Boolean out() {
        if (this.dataMap != null) {
            runFile(fileCreateCode);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 生成实体代码
     *
     * @param file 模板文件或目录
     */
    private void runFile(File file) {
        //第一步：判断是否是目录
        if (file.isDirectory()) {
            File[] f = file.listFiles();
            for (int i = 0; i < f.length; i++) {
                runFile(f[i]);
            }
        } else if (file.isFile()) {
            FileWriter out = null;
            //第二步： 是文件
            try {
                //第三步：
                File baseFile = new File(templatePath);
                String str = file.getAbsolutePath().substring(baseFile.getAbsolutePath().length());
                File baseCreateFile = new File(createFilePath);
                String createFile = baseCreateFile.getAbsolutePath() + stringTemplate(str, dataMap);
                System.out.println("=======================================================================================================================================");
                System.out.println("加载模板文件：" + str);
                System.out.println("正在生成文件：" + createFile);
                File f = new File(createFile);
                //第四步：判断目录是否存在--- 不存在不生成目录
                File fdir = new File(f.getParent());
                if (!fdir.exists()) {
                    fdir.mkdirs();
                }
                //第五步： 文件输出
                out = new FileWriter(f);
                Template temp = cfg.getTemplate(str);
                temp.process(dataMap, out);
                //第六步：结束，关闭
                out.flush();
                System.out.println("生成文件成功：" + createFile);
                System.out.println("=======================================================================================================================================");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public String stringTemplate(final String templateStr, final Map dataModel) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("myTemplate", templateStr);
        cfg.setTemplateLoader(stringLoader);
        Template template = cfg.getTemplate("myTemplate", "utf-8");
        StringWriter writer = new StringWriter();
        template.process(dataModel, writer);
        return writer.toString();
    }


    //=====================================    SET  、 GET


    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }
}
