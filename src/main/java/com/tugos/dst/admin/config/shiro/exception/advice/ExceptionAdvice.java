package com.tugos.dst.admin.config.shiro.exception.advice;

/**
 * 异常通知器接口
 */
public interface ExceptionAdvice {

    /**
     * 运行
     * @param e 异常对象
     */
    void run(RuntimeException e);
}
