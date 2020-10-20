package com.tugos.dst.admin.enums;

/**
 * @author qinming
 * @date 2020-10-01 21:54:50
 * <p> 停止服务器类型枚举 </p>
 */
public enum StopTypeEnum {

    /**
     * 类型 0 停止所有
     */
    STOP_ALL(0, "停止所有"),
    STOP_MASTER(1, "仅停止地面"),
    STOP_CAVES(2, "仅停止洞穴");

    private final Integer type;
    private final String desc;

    StopTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static StopTypeEnum get(Integer type) {
        StopTypeEnum[] values = StopTypeEnum.values();
        for (StopTypeEnum object : values) {
            if (object.type.equals(type)) {
                return object;
            }
        }
        return null;
    }

    public static String getDesc(Integer type) {
        StopTypeEnum[] values = StopTypeEnum.values();
        for (StopTypeEnum object : values) {
            if (object.type.equals(type)) {
                return object.desc;
            }
        }
        return null;
    }

}
