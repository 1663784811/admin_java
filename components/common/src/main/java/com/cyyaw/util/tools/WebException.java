package com.cyyaw.util.tools;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

@Data
public class WebException extends RuntimeException {

    private String msg;
    private Integer code;

    private WebException() {
    }

    private WebException(WebErrCodeEnum webErrCodeEnum) {
        this.msg = webErrCodeEnum.getMsg();
        this.code = webErrCodeEnum.getCode();
    }

    private WebException(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return StrUtil.isNotBlank(msg) ? msg : super.getMessage();
    }

    /**
     * 异常信息
     */
    public static void fail() {
        String msg = WebErrCodeEnum.WEB_ERR.getMsg();
        Integer code = WebErrCodeEnum.WEB_ERR.getCode();
        fail(code, msg);
    }

    /**
     * 异常信息
     *
     * @param msg 信息
     */
    public static void fail(String msg) {
        fail(WebErrCodeEnum.WEB_ERR.getCode(), msg);
    }

    /**
     * 异常信息
     */
    public static void fail(WebErrCodeEnum webErrCodeEnum) {
        throw new WebException(webErrCodeEnum);
    }

    /**
     * 异常信息
     */
    public static void fail(WebErrCodeEnum webErrCodeEnum, String msg) {
        Integer code = webErrCodeEnum.getCode();
        fail(code, msg);
    }

    /**
     * 异常信息
     */
    public static void fail(Integer code, String msg) {
        throw new WebException(msg, code);
    }


    // ========================================
    // ========================================
    // ========================================

    /**
     * 参数错误
     */
    public static void parameterFail() {
        fail(WebErrCodeEnum.WEB_ILLEGALSTATE);
    }
}
