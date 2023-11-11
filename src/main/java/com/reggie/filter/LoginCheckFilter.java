package com.reggie.filter;


import com.alibaba.fastjson.JSON;
import com.reggie.common.BaseContext;
import com.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;



//检查用户是否已经完成登录，这是过滤器。也可以去做个拦截器，作用是防止用户没有登录，就进去了
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        //1。获取本次请求的URI
        String requestURI=request.getRequestURI();///backend/index.html

        log.info("拦截到请求：{}", requestURI);


        //定义不需要处理的请求路径，退出和登录路径，以及前端和后台的静态页面
        String[] urls=new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",//为什么这里/backend/**只通配符到静态页面，而没有匹配到动态页面，/bankend/index.html算静态页面吗？要拦截吗
                "/front/**"
        };

        //2。判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        //3。如果不需要处理，则直接放行
        if (check) {
            log.info("本次请求{}不需要处理", requestURI);
            //放行
            filterChain.doFilter(request, response);
            return;
        }

        //4。判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已登录，用户id为：{}", request.getSession().getAttribute("employee"));
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request, response);
            return;
        }
        log.info("用户未登录");

        //5。如果未登录则返回未登录信息，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOT LOGIN")));
        return;
    }

    //路径匹配，检查本次请求是否需要放行
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }

}
