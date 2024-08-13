package com.cyyaw.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 *
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                // 不需要生成密码所以返回 null
                return null;
            }
        };
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //  跨域问题
        http.cors().and()
                .csrf().disable()
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // ========================================================  过滤器列表  START
                // ========================================================
                // ========================================================
                // ========================================================
                // ========================================================
                // ========================================================
                .addFilterBefore(new JsonWebTokenFilter(), BasicAuthenticationFilter.class)
                // =========   过滤器列表  END
                // =========   异常处理列表  START
                .exceptionHandling()
                // 认证异常处理
                .authenticationEntryPoint(new JWTAuthenticationEntryPoint())
                //添加无权限时的处理
                .accessDeniedHandler(new JWTAccessDeniedHandler())
                .and()
                // ========================================================   异常处理列表  END
                // ========================================================
                // ========================================================
                // ========================================================
                // ========================================================
                // ========================================================
                //  受权
                .authorizeRequests()
                .antMatchers(
                        "/v2/api-docs",
                        "/doc.html",
                        "/webjars/**",
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources",
                        "/swagger-resources/configuration/security",
                        "/swagger-ui.html",
                        "/home/**",
                        "/wx/user/**/login",
                        "/login/**",
                        "/activiti/**",
                        // ==============================================================   admin
                        "/admin/enterprise/findPage",
                        "/admin/file/**",
                        "/admin/*/common/query",
                        "/admin/*/login/**",
                        "/admin/phone/phone/**",
                        "/admin/**",

                        // ============================================================== appAdmin
                        "/appAdmin/*/login/**",
                        "/appAdmin/*/store/*/login",
                        "/appAdmin/*/common/query",


                        // ============================================================== app
                        "/app/*/common/**",
                        "/app/*/login/**",
                        "/app/*/food/websocket/**",
                        "/app/*/store/*/food/**",
                        "/app/*/shopping/**",  //   商城
                        "/app/*/order/countGoodsPrice",


                        // ============================================================== 页面
                        "/enterprise/**",
                        "/rabbitmq/**"



                ).permitAll()
                // 每一个请求都要验证
                .anyRequest().authenticated().and();
    }
}
