package com.cyyaw.util.tools;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResult<T> implements Serializable {

    /**
     * 分页信息
     */
    private Result result;
    /**
     * 主要数据 可能是Object 或  Arr
     */
    private T data;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态返回
     */
    public static BaseResult rest(WebErrCodeEnum codeEnum) {
        return createResult(codeEnum, null, null, null);
    }

    /**
     * 状态返回
     */
    public static BaseResult rest(WebErrCodeEnum codeEnum, String msg) {
        return createResult(codeEnum, null, null, msg);
    }

    /**
     * 成功
     */
    public static BaseResult ok() {
        return createResult(WebErrCodeEnum.WEB_SUCCESS, null, null, null);
    }

    /**
     * 成功
     *
     * @param data 数据
     */
    public static BaseResult ok(Object data) {
        return createResult(WebErrCodeEnum.WEB_SUCCESS, data, null, null);
    }

    /**
     * 成功
     *
     * @param data 数据
     * @param msg  提示
     * @return
     */
    public static BaseResult ok(Object data, String msg) {
        return createResult(WebErrCodeEnum.WEB_SUCCESS, data, null, msg);
    }

    /**
     * 成功
     *
     * @param data   数据
     * @param result 分页信息
     * @return
     */
    public static BaseResult ok(Object data, Result result) {
        return createResult(WebErrCodeEnum.WEB_SUCCESS, data, result, null);
    }


    /**
     * 成功
     */
    public static BaseResult ok(PageRespone pageRespone) {
        return ok(pageRespone, (String) null);
    }

    /**
     * 成功
     */
    public static BaseResult ok(CommonRest commonRest) {
        Long total = Long.valueOf(commonRest.getTotal());
        Result result = new Result();
        result.setTotal(total);
        result.setPage(commonRest.getPage());
        result.setSize(commonRest.getSize());
        String msg = commonRest.getMsg();
        return createResult(WebErrCodeEnum.WEB_SUCCESS, commonRest.getData(), result, msg);
    }

    /**
     * 成功
     */
    public static BaseResult ok(PageRespone pageRespone, String msg) {
        if (null != pageRespone) {
            return createResult(
                    WebErrCodeEnum.WEB_SUCCESS, pageRespone.getContent(),
                    new Result(pageRespone.getPage(), pageRespone.getSize(), pageRespone.getTotal()), msg
            );
        } else {
            return createResult(WebErrCodeEnum.WEB_SUCCESS, null, null, msg);
        }
    }

    /**
     * 成功
     *
     * @param data   数据
     * @param result 分页信息
     * @return
     */
    public static BaseResult ok(Object data, Result result, String msg) {
        return createResult(WebErrCodeEnum.WEB_SUCCESS, data, result, msg);
    }


    /**
     * 失败
     */
    public static BaseResult fail() {
        return createResult(WebErrCodeEnum.WEB_ERR, null, null, null);
    }

    /**
     * 失败
     *
     * @param msg 信息
     */
    public static BaseResult fail(String msg) {
        return createResult(WebErrCodeEnum.WEB_ERR, null, null, msg);
    }


    private static BaseResult createResult(WebErrCodeEnum codeEnum, Object data, Result result, String msg) {
        BaseResult res = new BaseResult();
        res.setCode(codeEnum.getCode());
        if (null != msg && !"".equals(msg)) {
            res.setMsg(msg);
        } else {
            res.setMsg(codeEnum.getMsg());
        }
        res.setData(data);
        res.setResult(result);
        return res;
    }

    @Data
    public static class Result {
        private Long total;
        private Integer page;
        private Integer size;

        public Result() {
        }

        public Result(Integer page, Integer size, Long total) {
            this.total = total;
            this.page = page;
            this.size = size;
        }
    }
}