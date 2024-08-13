package com.cyyaw.user.config;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.WebErrCodeEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 没有认证
 */
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        WebErrCodeEnum err = WebErrCodeEnum.WEB_NOT_LOGIN;
        BaseResult rest = BaseResult.rest(err);
        String json = JSONUtil.toJsonStr(new JSONObject(rest));
        response.getWriter().write(json);
    }
}
