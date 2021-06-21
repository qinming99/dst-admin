package com.tugos.dst.admin.vo;

import lombok.Data;

/**
 * @author qinming
 * @date 2021-06-19 14:26:36
 * <p> 游戏端口设置 </p>
 */
@Data
public class GamePortVO {

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
