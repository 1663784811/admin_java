package com.cyyaw.user.config;

import cn.hutool.json.JSONObject;
import com.cyyaw.config.exception.WebException;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.util.tools.JwtTokenUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * JWT 受权
 */
@Slf4j
public class JsonWebTokenFilter implements Filter {


    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String tokenHeader = httpRequest.getHeader(JwtTokenUtils.TOKEN_HEADER);
        // 如果请求头中没有Authorization信息则直接放行了
        if (!ObjectUtils.isEmpty(tokenHeader) && tokenHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
            try {
                SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 这里从token中获取用户信息并新建一个token
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        boolean verifier = JwtTokenUtils.verifierToken(token);
        if (verifier) {
            String json = JwtTokenUtils.getClaim(token);
            LoginInfo loginInfo = new JSONObject(json).toBean(LoginInfo.class);
            String role = loginInfo.getRole();
            String account = loginInfo.getAccount();
            if (ObjectUtils.isEmpty(role)) {
                return new UsernamePasswordAuthenticationToken(account, null, Collections.singleton(new SimpleGrantedAuthority("user")));
            } else {
                return new UsernamePasswordAuthenticationToken(account, null, Collections.singleton(new SimpleGrantedAuthority(role)));
            }
        }
        return null;
    }
}
