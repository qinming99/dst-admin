package com.tugos.dst.admin.vo;


import lombok.Data;

/**
 * @author qinming
 * @date 2020-05-17
 * <p> 游戏房间信息vo </p>
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
     * 1 进保存 2 启动新游戏 3 保存并重启
     */
    private Integer type;


}
