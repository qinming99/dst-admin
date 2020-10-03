package com.tugos.dst.admin.vo;


import lombok.Data;

/**
 * 游戏设置信息
 */
@Data
public class GameConfigVO {

    private String clusterIntention;

    private String clusterName;

    private String clusterDescription;

    private String gameMode;

    private Boolean pvp;

    private Integer maxPlayers;

    private String clusterPassword;

    private String token;

    private String masterMapData;

    private String cavesMapData;

    private String modData;
    /**
     * 1 进保存 2 启动新游戏
     */
    private Integer type;


}
