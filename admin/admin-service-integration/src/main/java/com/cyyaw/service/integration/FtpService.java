package com.cyyaw.service.integration;

public interface FtpService {

    /**
     * 上传文件
     */
    String uploadFile(String filePath);

    /**
     * 下载文件
     */
    String downloadFile(String filePath);


}
