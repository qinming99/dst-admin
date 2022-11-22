package com.tugos.dst.admin.vo;

import lombok.Data;

import java.util.List;


/**
 * @author qinming
 * @date 2022-11-21 20:57:57
 * <p> 游戏的存档信息 服务器使用详细 </p>
 */
@Data
public class GameArchiveVO {

    /**
     * 房间名称
     */
    private String clusterName;

    /**
     * 游戏模式 生存，无尽，荒野
     */
    private String gameMode;

    /**
     * 最大玩家数量
     */
    private Integer maxPlayers;

    /**
     * 房间密码
     */
    private String clusterPassword;

    /**
     * 存档的天数
     */
    private String playDay;

    /**
     * 存档的季节
     */
    private String season;

    /**
     * MOD总数
     */
    private Integer totalModNum;

    /**
     * MOD列表信息
     */
    private List<String> modNos;

    /**
     * MOD原始信息
     */
    private String modContent;


}
