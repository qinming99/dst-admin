package com.tugos.dst.admin.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qinming
 * @date 2020-09-27 19:50:10
 * <p>统一返回结果</p>
 */
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 数据集
     */
    private T data;

    /**
     * 提示信息
     */
    private String message;

    private ResultVO(ResultInterface resultCode) {
        this(resultCode, null, resultCode.getMessage());
    }

    private ResultVO(ResultInterface resultCode, String msg) {
        this(resultCode, null, msg);
    }

    private ResultVO(ResultInterface resultCode, T data) {
        this(resultCode, data, resultCode.getMessage());
    }

    private ResultVO(ResultInterface resultCode, T data, String msg) {
        this(resultCode.getCode(), data, msg);
    }

    private ResultVO(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.message = msg;
    }

    public static <T> ResultVO<T> data(T data) {
        return data(data, ApiConstant.SUCCESS_MESSAGE);
    }


    public static <T> ResultVO<T> data(T data, String msg) {
        return data(ResultCodeEnum.SUCCESS.code, data, msg);
    }


    public static <T> ResultVO<T> data(int code, T data, String msg) {
        return new ResultVO<>(code, data, data == null ? ApiConstant.SUCCESS_MESSAGE : msg);
    }


    public static <T> ResultVO<T> success() {
        return new ResultVO<>(ResultCodeEnum.SUCCESS);
    }

    public static <T> ResultVO<T> success(String msg) {
        return new ResultVO<>(ResultCodeEnum.SUCCESS, msg);
    }


    public static <T> ResultVO<T> success(ResultCodeEnum resultCode) {
        return new ResultVO<>(resultCode);
    }

    public static <T> ResultVO<T> success(ResultCodeEnum resultCode, String msg) {
        return new ResultVO<>(resultCode, msg);
    }


    public static <T> ResultVO<T> fail(String msg) {
        return new ResultVO<>(ResultCodeEnum.FAILURE, msg);
    }

    public static <T> ResultVO<T> fail(int code, String msg) {
        return new ResultVO<>(code, null, msg);
    }

    public static <T> ResultVO<T> fail(ResultCodeEnum resultCode) {
        return new ResultVO<>(resultCode);
    }

    public static <T> ResultVO<T> fail(ResultCodeEnum resultCode, String msg) {
        return new ResultVO<>(resultCode, msg);
    }
}
