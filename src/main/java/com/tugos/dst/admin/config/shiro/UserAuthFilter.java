package com.tugos.dst.admin.config.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理session超时问题拦截器
 */
public class UserAuthFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = getSubject(request, response);
            return subject.getPrincipal() != null;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        HttpServletResponse httpResponse = WebUtils.toHttp(response);

        if (httpRequest.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equalsIgnoreCase(httpRequest.getHeader("X-Requested-With"))) {
            httpResponse.sendError(HttpStatus.UNAUTHORIZED.value());
        } else {
            redirectToLogin(request, response);
        }
        return false;
    }
}
