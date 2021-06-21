package com.tugos.dst.admin.utils;

import com.tugos.dst.admin.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author qinming
 * @date 2020-10-25 23:26:20
 * <p> 无 </p>
 */
@Data
public class DstConfigDataTable implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 定时更新游戏任务
     */
    private Map<String, Integer> SCHEDULE_UPDATE_MAP;

    /**
     * 定时备份游戏任务
     */
    private Map<String, Integer> SCHEDULE_BACKUP_MAP;

    private User USER_INFO;

    /**
     * 不启动地面标志
     */
    private Boolean notStartMaster;

    /**
     * 不启动洞穴标志
     */
    private Boolean notStartCaves;

    /**
     * 智能更新标志
     */
    private Boolean smartUpdate;

    /**
     * 主端口号
     */
    private String masterPort;

    /**
     * 地面端口号
     */
    private String groundPort;

    /**
     * 洞穴端口号
     */
    private String cavesPort;
}
