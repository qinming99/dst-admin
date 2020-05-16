package com.tugos.dst.admin.enums;


import com.tugos.dst.admin.utils.StatusConst;
import lombok.Getter;

/**
 * 数据状态枚举-用于逻辑删除控制
 */
@Getter
public enum StatusEnum {

    /**
     * 正常的数据
     */
    OK(StatusConst.OK, "正常"),
    /**
     * 被冻结的数据，不可用
     */
    FREEZED(StatusConst.FREEZED, "冻结"),
    /**
     * 数据已被删除
     */
    DELETE(StatusConst.DELETE, "删除");

    private Byte code;

    private String message;

    StatusEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }
}

