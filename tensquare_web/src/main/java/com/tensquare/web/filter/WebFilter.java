package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 网关过滤器
 */
@Component
public class WebFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        // 得到request 上下文
        RequestContext context = RequestContext.getCurrentContext();

        //得到Request域
        HttpServletRequest request = context.getRequest();

        //得到头信息
        String header = request.getHeader("Authorization");

        if (header != null && !"".equals(header)) {
            context.addZuulRequestHeader("Authorization",header);
        }

        return null;
    }
}
