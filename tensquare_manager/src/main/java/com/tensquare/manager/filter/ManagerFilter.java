package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 网关过滤器
 */
@Component
public class ManagerFilter extends ZuulFilter {

    @Resource
    private JwtUtil jwtUtil;


    /**
     * 在请求前pre或者后post执行
     *
     * @return 前 pre 后 post
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * @return 过滤器的执行优先级, 数字越小, 优先级越高
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否开启 true表示开启
     *
     * @return true开启  false表示关闭
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作 return 任何Object的值都表示继续执行.
     * setesendzullRespponse(false)表示不再执行
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过了后台过滤器");
        RequestContext currentContext = RequestContext.getCurrentContext();
        //request 域
        HttpServletRequest request = currentContext.getRequest();

        //对OPTIONS方法放行
        if (request.getMethod().equals("OPTIONS")) {
            return null;
        }

        //对login请求放行
        if (request.getRequestURI().indexOf("login") > 0) {
            return null;
        }

        //得到头信息
        String authorization = request.getHeader("Authorization");

        if (!StringUtils.isEmpty(authorization)) {
            if (authorization.startsWith("Bearer ")) {
                String token = authorization.substring(7);
                try {
                    // 解析token令牌
                    Claims claims = jwtUtil.parseJWT(token);
                    //得到角色
                    String roles = (String) claims.get("roles");
                    if (roles.equals("admin")) {
                        //  把头信息转发下去并且放行
                        currentContext.addZuulRequestHeader("Authorization", authorization);
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // 终止运行
                    currentContext.setSendZuulResponse(false);
                    currentContext.setResponseBody("token令牌过期");
                }
            }

        }
        // 终止运行
        currentContext.setSendZuulResponse(false);
        //发送响应码
        currentContext.setResponseStatusCode(403);
        currentContext.setResponseBody("权限不足");
        //设置编码
        currentContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
