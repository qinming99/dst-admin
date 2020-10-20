package com.tugos.dst.admin.enums;

/**
 * @author qinming
 * @date 2020-10-01 21:54:50
 * <p> 启动服务器类型枚举 </p>
 */
public enum StartTypeEnum {

    /**
     * 类型 0 启动所有
     */
    START_ALL(0, "启动所有"),
    START_MASTER(1, "仅启动地面"),
    START_CAVES(2, "仅启动洞穴");

    public final Integer type;
    public final String desc;

    StartTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static StartTypeEnum get(Integer type) {
        StartTypeEnum[] values = StartTypeEnum.values();
        for (StartTypeEnum object : values) {
            if (object.type.equals(type)) {
                return object;
            }
        }
        return null;
    }

    public static String getDesc(Integer type) {
        StartTypeEnum[] values = StartTypeEnum.values();
        for (StartTypeEnum object : values) {
            if (object.type.equals(type)) {
                return object.desc;
            }
        }
        return null;
    }

}
