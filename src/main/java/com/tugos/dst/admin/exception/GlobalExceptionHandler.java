package com.tugos.dst.admin.exception;

import com.tugos.dst.admin.common.ResultCodeEnum;
import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qinming
 * @date 2020-10-03 20:41:51
 * <p> 自定义全局异常处理 </p>
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 拦截访问权限异常
     */
    @ExceptionHandler(AuthorizationException.class)
    public ResultVO<String> authorizationException(AuthorizationException exception,
                                                   HttpServletRequest request, HttpServletResponse response) {
        // 获取异常信息
        Throwable cause = exception.getCause();
        String message = cause.getMessage();
        // 判断无权限访问的方法返回对象是否为ResultVO
        if (!message.contains(ResultVO.class.getName())) {
            try {
                // 重定向到无权限页面
                String contextPath = request.getContextPath();
                ShiroFilterFactoryBean shiroFilter = SpringContextUtil.getBean(ShiroFilterFactoryBean.class);
                response.sendRedirect(contextPath + shiroFilter.getUnauthorizedUrl());
            } catch (IOException e1) {
                ResultVO.fail(ResultCodeEnum.NO_PERMISSIONS);
            }
        }
        return ResultVO.fail(ResultCodeEnum.NO_PERMISSIONS);
    }

    /**
     * 405 - Method Not Allowed
     * 带有@ResponseStatus注解的异常类会被ResponseStatusExceptionResolver 解析
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultVO<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("", e);
        return ResultVO.fail(ResultCodeEnum.METHOD_NOT_ALLOWED);
    }

    /**
     * 参数校验异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<Map> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>(16);
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ResultVO<Map> fail = ResultVO.fail(ResultCodeEnum.PARAM_ERR);
        fail.setData(errors);
        return fail;
    }

    /**
     * 拦截自定义异常
     */
    @ExceptionHandler(ResultException.class)
    public ResultVO<String> resultException(ResultException e) {
        return ResultVO.fail(e.getCode(),e.getMessage());
    }

}
