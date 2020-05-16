package com.tugos.dst.admin.shiro.exception;


import com.tugos.dst.admin.enums.ResultEnum;
import com.tugos.dst.admin.utils.ResultVoUtil;
import com.tugos.dst.admin.utils.SpringContextUtil;
import com.tugos.dst.admin.vo.ResultVo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截访问权限异常处理
 */
@ControllerAdvice
@Order(-1)
public class AuthorizationExceptionHandler {

    /**
     * 拦截访问权限异常
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public ResultVo authorizationException(AuthorizationException e, HttpServletRequest request,
                                           HttpServletResponse response){
        Integer code = ResultEnum.NO_PERMISSIONS.getCode();
        String msg = ResultEnum.NO_PERMISSIONS.getMessage();

        // 获取异常信息
        Throwable cause = e.getCause();
        String message = cause.getMessage();
        Class<ResultVo> resultVoClass = ResultVo.class;

        // 判断无权限访问的方法返回对象是否为ResultVo
        if(!message.contains(resultVoClass.getName())){
            try {
                // 重定向到无权限页面
                String contextPath = request.getContextPath();
                ShiroFilterFactoryBean shiroFilter = SpringContextUtil.getBean(ShiroFilterFactoryBean.class);
                response.sendRedirect(contextPath+shiroFilter.getUnauthorizedUrl());
            } catch (IOException e1) {
                return ResultVoUtil.error(code, msg);
            }
        }
        return ResultVoUtil.error(code, msg);
    }
}
