package com.cyyaw.user.config;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.util.tools.JwtTokenUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 登录信息参数解释器
 */
public class HandlerMethodArgumentResolverLoginInfo implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.getParameterType().isAssignableFrom(LoginInfo.class) && parameter.hasParameterAnnotation(TokenData.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        try {
            if (parameter.getParameterType().isAssignableFrom(LoginInfo.class) && parameter.hasParameterAnnotation(TokenData.class)) {
                String token = webRequest.getHeader(JwtTokenUtils.TOKEN_HEADER);
                if (StrUtil.isNotBlank(token)) {
                    if (token.indexOf(JwtTokenUtils.TOKEN_PREFIX) == 0) {
                        String tokenData = token.replace(JwtTokenUtils.TOKEN_PREFIX, "");
                        String json = JwtTokenUtils.getClaim(tokenData);
                        LoginInfo loginInfo = new JSONObject(json).toBean(LoginInfo.class);
                        return loginInfo;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
