package com.aliang.wenda.interceptor;

import com.aliang.wenda.model.HostHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginRequiredInterceptor
 * @Author Aliang
 * @Date 2018/8/8 23:10
 * @Version 1.0
 **/
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor{

    private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);

    @Autowired
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String urlString = httpServletRequest.getRequestURI();

        if(hostHolder.getUser() == null){
            httpServletResponse.sendRedirect("/wenda/reglogin?next=" + httpServletRequest.getRequestURI());
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
