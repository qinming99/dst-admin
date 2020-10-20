package com.tugos.dst.admin.vo;

import com.tugos.dst.admin.entity.serverInfo.Cpu;
import com.tugos.dst.admin.entity.serverInfo.Mem;
import lombok.Data;

import java.util.List;

/**
 * @author qinming
 * @date 2020-09-29 21:14:24
 * <p> 饥荒服务器监控信息 </p>
 */
@Data
public class DstServerInfoVO {

    /**
     * 地面状态 true 启动
     */
    private Boolean masterStatus;

    /**
     * 洞穴状态 true 启动
     */
    private Boolean cavesStatus;

    /**
     * CPU相关信息
     */
    private Cpu cpu;

    /**
     * 內存相关信息
     */
    private Mem mem;

    /**
     * 备份的存档名称
     */
    private List<String> backupList;


}
