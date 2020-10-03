package com.tugos.dst.admin.exception;


import com.tugos.dst.admin.common.ResultCodeEnum;
import com.tugos.dst.admin.common.ResultInterface;
import lombok.Getter;

/**
 * 自定义异常对象
 */
@Getter
public class ResultException extends RuntimeException {

    private int code;

    /**
     * 统一异常处理
     * @param resultEnum 状态枚举
     */
    public ResultException(ResultCodeEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    /**
     * 统一异常处理
     * @param resultEnum 枚举类型，需要实现结果枚举接口
     */
    public ResultException(ResultInterface resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    /**
     * 统一异常处理
     * @param code    状态码
     * @param message 提示信息
     */
    public ResultException(int code, String message) {
        super(message);
        this.code = code;
    }

}
