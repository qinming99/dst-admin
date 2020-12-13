package com.tugos.dst.admin.enums;

/**
 * @author qinming
 * @date 2020-11-15 22:00:00
 * <p> dst日志类型 </p>
 */
public enum DstLogTypeEnum {

    /**
     * 类型 0 地面运行日志，1洞穴运行日志，2玩家聊天记录
     */
    MASTER_LOG(0, "地面运行日志"),
    CAVES_LOG(1, "洞穴运行日志"),
    CHAT_LOG(2, "玩家聊天记录");

    public final Integer type;
    public final String desc;

    DstLogTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static DstLogTypeEnum get(Integer type) {
        DstLogTypeEnum[] values = DstLogTypeEnum.values();
        for (DstLogTypeEnum object : values) {
            if (object.type.equals(type)) {
                return object;
            }
        }
        return null;
    }

    public static String getDesc(Integer type) {
        DstLogTypeEnum[] values = DstLogTypeEnum.values();
        for (DstLogTypeEnum object : values) {
            if (object.type.equals(type)) {
                return object.desc;
            }
        }
        return null;
    }

}
