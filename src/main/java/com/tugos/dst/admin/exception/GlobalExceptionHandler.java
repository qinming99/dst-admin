package com.tugos.dst.admin.exception;


import com.tugos.dst.admin.common.ResultCodeEnum;
import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.utils.SafeLoginCheckUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
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
    @ExceptionHandler(ShiroException.class)
    public ResultVO<String> authorizationException(ShiroException exception) {
        log.error("ShiroException异常：", exception);
        return ResultVO.fail(ResultCodeEnum.NO_PERMISSIONS);
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    @ResponseBody
    public ResultVO<String> handleIncorrectCredentialsException(HttpServletRequest request, IncorrectCredentialsException e) {
        log.error("shiro 账号或密码错误：{}", e.getMessage());
        SafeLoginCheckUtils.loginErrorRecord(request);
        return ResultVO.fail(String.format(ResultCodeEnum.NOT_EXIST_USER_OR_ERROR_PWD.getMessage(),
                SafeLoginCheckUtils.getRemainingTimes(request)));
    }

    /**
     * 405 - Method Not Allowed
     * 带有@ResponseStatus注解的异常类会被ResponseStatusExceptionResolver 解析
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultVO<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("405异常", e);
        return ResultVO.fail(ResultCodeEnum.METHOD_NOT_ALLOWED);
    }

    /**
     * 参数校验异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>(16);
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ResultVO<Map<String, String>> fail = ResultVO.fail(ResultCodeEnum.PARAM_ERR);
        fail.setData(errors);
        return fail;
    }

    /**
     * 拦截自定义异常
     */
    @ExceptionHandler(ResultException.class)
    public ResultVO<String> resultException(ResultException e) {
        return ResultVO.fail(e.getCode(), e.getMessage());
    }

}
