package com.tensquare.user.interceptor;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private JwtUtil jwtUtil;


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("过来了");
        //获取头
        String header = request.getHeader("Authorization");
        //如果头不是空
        if (!StringUtils.isEmpty(header)) {
            //如果是以规定的字符串开始
            if (header.startsWith("Bearer ")) {
                //截取字符串,得到token令牌
                String token = header.substring(7);

                //对令牌进行验证
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    //得到角色
                    String roles = (String) claims.get("roles");
                    //如果是admin
                    if (roles != null && roles.equals("admin")) {
                        //把token 存到请求域中
                        request.setAttribute("claims_admin", token);
                    }
                    //如果是user
                    if (roles != null && roles.equals("user")) {
                        //把token 存到请求域中
                        request.setAttribute("claims_user", token);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("令牌不正确!");
                }
            }
        }
        return true;
    }
}
