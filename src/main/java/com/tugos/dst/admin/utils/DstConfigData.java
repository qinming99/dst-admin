package com.tugos.dst.admin.utils;

import com.google.common.collect.Maps;
import com.tugos.dst.admin.entity.User;

import java.util.Map;

/**
 * @author qinming
 * @date 2020-10-25 23:13:42
 * <p> 本地数据库 </p>
 */
public class DstConfigData {

    /**
     * 定时更新游戏任务
     */
    public static final Map<String, Integer> SCHEDULE_UPDATE_MAP = Maps.newHashMap();

    /**
     * 定时备份游戏任务
     */
    public static final Map<String, Integer> SCHEDULE_BACKUP_MAP = Maps.newHashMap();

    public static final User USER_INFO = new User();


    /**
     * 不启动地面标志
     */
    public static Boolean notStartMaster;

    /**
     * 不启动洞穴标志
     */
    public static Boolean notStartCaves;

    /**
     * 智能更新标志
     */
    public static Boolean smartUpdate;

    /**
     * 主端口号
     */
    public static String masterPort;

    /**
     * 地面端口号
     */
    public static String groundPort;

    /**
     * 洞穴端口号
     */
    public static String cavesPort;

    /**
     * 清理所有数据
     */
    public static void clearAllData(){
        SCHEDULE_UPDATE_MAP.clear();
        SCHEDULE_BACKUP_MAP.clear();
    }

}
