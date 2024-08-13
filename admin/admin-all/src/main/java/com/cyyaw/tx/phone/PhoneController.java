package com.cyyaw.tx.phone;


import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Api(tags = "公共模块")
@RestController
@RequestMapping("/tx/phone/phone")
public class PhoneController {


    @Autowired
    private PhoneService phoneService;


    @ApiOperation(value = "查看手机列表", notes = "查看手机列表")
    @GetMapping("/phoneList")
    public BaseResult phoneList() {
        List<PhoneEntity> data = phoneService.phoneList();
        return BaseResult.ok(data);
    }

    @ApiOperation(value = "获取手机截图", notes = "获取手机截图")
    @GetMapping("/phoneImage/{phone}")
    public void phoneImage(@PathVariable String phone, HttpServletResponse response) {
        String path = phoneService.phoneImage(phone);
        // ftp 下载文件
        File file = new File(path);
        byte[] buffer = new byte[512];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            if (file.exists()) {
                response.setCharacterEncoding("UTF-8");
                response.setHeader("content-disposition", "attachment; filename=" + phone + ".png");
                response.setContentType("image/**;charset=UTF-8");
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


    @ApiOperation(value = "获取当前页面结构", notes = "获取当前页面结构")
    @GetMapping("/phoneStructure/{phone}")
    public BaseResult phoneStructure(@PathVariable String phone) {
        String str = phoneService.phoneStructure(phone);
        return BaseResult.ok(str);
    }


}
