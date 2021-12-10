package com.tugos.dst.admin.common;

import com.tugos.dst.admin.config.I18nResourcesConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qinming
 * @date 2020-09-27 14:17:04
 * <p> 返回状态集枚举 </p>
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum implements ResultInterface {

    /**
     * 成功
     */
    SUCCESS(ApiConstant.OK, "成功"),
    FAILURE(ApiConstant.ERROR, "系统异常"),

    SYSTEM_ERR(500, "服务器运行异常"),
    REQUEST_NOT_FOUND(404, "访问地址不存在"),
    METHOD_NOT_ALLOWED(405, "请求方式错误"),

    //用户相关
    USER_NAME_PWD_NULL(11000, "用户名和密码不能为空"),
    USER_CAPTCHA_ERROR(11001, "验证码错误"),
    NO_PERMISSIONS(11002, "权限不足！"),
    NOT_EXIST_USER_OR_ERROR_PWD(11005, "用户不存在或密码错误,你还有%s次机会重试"),
    USER_HAS_FREEZE(11006, "用户已被冻结，请稍后重试"),

    //系统相关
    PARAM_ERR(12000,"请求参数异常"),
    ;

    final int code;

    private final String message;


    @Override
    public String getMessage() {
        //获取当前语言环境
        return I18nResourcesConfig.getMessage("tip.code.message."+this.code);
    }

    @Override
    public int getCode() {
        return this.code;
    }


}
