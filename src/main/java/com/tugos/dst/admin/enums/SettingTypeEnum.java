package com.tugos.dst.admin.enums;

/**
 * @author qinming
 * @date 2020-10-02 15:50:52
 * <p> 设置房间的启动类型 </p>
 */
public enum SettingTypeEnum {

    /**
     * 类型 0 仅保存
     */
    SAVE(1, "仅保存"),
    START_GAME(2, "启动新游戏"),
    SAVE_RESTART(3, "保存并重启");

    public final Integer type;
    public final String desc;

    SettingTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static SettingTypeEnum get(Integer type) {
        SettingTypeEnum[] values = SettingTypeEnum.values();
        for (SettingTypeEnum object : values) {
            if (object.type.equals(type)) {
                return object;
            }
        }
        return null;
    }

    public static String getDesc(Integer type) {
        SettingTypeEnum[] values = SettingTypeEnum.values();
        for (SettingTypeEnum object : values) {
            if (object.type.equals(type)) {
                return object.desc;
            }
        }
        return null;
    }

}
