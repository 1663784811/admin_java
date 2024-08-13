package com.cyyaw.config.exception;

import com.cyyaw.util.tools.WebErrCodeEnum;
import com.cyyaw.util.tools.WhyException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class CustomException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //判断是否是异步请求
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        if (ex instanceof IllegalStateException) {
            mav.addObject("code", WebErrCodeEnum.WEB_ILLEGALSTATE.getCode());
            mav.addObject("msg", WebErrCodeEnum.WEB_ILLEGALSTATE.getMsg());
        } else if (ex instanceof ConstraintViolationException) {
            mav.addObject("code", WebErrCodeEnum.DATA_ERR_RELATION.getCode());
            mav.addObject("msg", WebErrCodeEnum.DATA_ERR_RELATION.getMsg());
        } else if ("The current Subject is not authenticated.  Access denied.".equals(ex.getMessage())) {
            mav.addObject("code", WebErrCodeEnum.WEB_LOGINERR.getCode());
            mav.addObject("msg", WebErrCodeEnum.WEB_LOGINERR.getMsg());
            mav.addObject("success", false);
        } else if (ex instanceof AuthenticationException) {
            mav.addObject("code", WebErrCodeEnum.WEB_LOGINERR.getCode());
            mav.addObject("msg", WebErrCodeEnum.WEB_LOGINERR.getMsg());
        } else if (ex instanceof WhyException) {
            WhyException whyException = (WhyException) ex;
            mav.addObject("code", whyException.getCode());
            mav.addObject("msg", whyException.getMessage());
        } else if (ex instanceof SQLException) {
            mav.addObject("code", 500);
            mav.addObject("msg", ex.getMessage());
        } else if(ex instanceof WebException){
            mav.addObject("code", 500);
            mav.addObject("msg", ((WebException) ex).getMsg());
        } else {
            mav.addObject("code", 500);
            mav.addObject("msg", "系统异常");
        }
        ex.printStackTrace();
        return mav;
    }
}
