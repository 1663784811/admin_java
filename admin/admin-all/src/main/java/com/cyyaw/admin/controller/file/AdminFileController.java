package com.cyyaw.admin.controller.file;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.WhyStringUtil;
import com.cyyaw.web.service.WebImageService;
import com.cyyaw.web.table.entity.WebImage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@Slf4j
@RestController
@Api(tags = "文件上传下载")
@RequestMapping("/admin/{eCode}/file")
public class AdminFileController {

    @Autowired
    private WebImageService webImageService;


    @Value("${cyyaw.file-path}")
    private String rootPath;


    @ApiOperation(value = "上传文件", notes = "上传文件")
    @PostMapping("/upload")
    public BaseResult upload(@RequestParam("file") MultipartFile uploadFile, @PathVariable String eCode) throws IOException {
        if (!uploadFile.isEmpty()) {
            // 上传到本地
            // 生成目录    //  /xxxx/年月/年月日/文件
            DateTime date = DateUtil.date();
            int year = date.year();
            int month = date.monthBaseOne();
            int day = date.dayOfMonth();
            String basePath = "/" + eCode + "/" + year + "-" + month + "/" + year + "-" + month + "-" + day;
            File dir = new File(rootPath + basePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //
            String fileName = WhyStringUtil.getRandomString(5) + "_" + uploadFile.getOriginalFilename();
            uploadFile.transferTo(new File(rootPath + basePath, fileName));
            // 上传到影像平台

            // 收集信息保存到影像表
            WebImage webImage = new WebImage();
            webImage.setTid(WhyStringUtil.getUUID());
            webImage.setCreateTime(new Date());
            webImage.setDel(0);
            webImage.setWebImageTypeId("");
            webImage.setPath(basePath + "/" + fileName);
            webImage.setName(fileName);
            webImage.setType(0);
            WebImage save = webImageService.save(webImage);
            // 返回影像ID
            return BaseResult.ok(save);
        }
        return BaseResult.fail();
    }


    @ApiOperation(value = "下载文件", notes = "下载文件")
    @GetMapping("/download")
    public void download(String code, HttpServletResponse response) {
        WebImage webImage = webImageService.findByTid(code);
        String path = webImage.getPath();
        Integer type = webImage.getType();
        String name = webImage.getName();
        // ftp 下载文件
        File file = new File(rootPath + path);
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            if (file.exists()) {
                response.setCharacterEncoding("UTF-8");
                response.setHeader("content-disposition", "attachment; filename=" + name);
                if (type == 0) {
                    response.setContentType("image/png;charset=UTF-8");
                } else if (type == 1) {
                    response.setContentType("text/html;charset=UTF-8");
                } else {
                    response.setContentType("image/**;charset=UTF-8");
                }
                os = response.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(file));
                while (bis.read(buffer) != -1) {
                    os.write(buffer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
