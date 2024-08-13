package com.cyyaw.user.config;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.WebErrCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 没有访问权限
 */
public class JWTAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        WebErrCodeEnum err = WebErrCodeEnum.WEB_AUTHENTICATION_ERR;
        BaseResult rest = BaseResult.rest(err);
        String json = JSONUtil.toJsonStr(new JSONObject(rest));
        httpServletResponse.getWriter().write(json);
    }
}
