package com.tugos.dst.admin.vo;


import lombok.Data;

@Data
public class GameConfigVO {

    private String clusterIntention;

    private String clusterName;

    private String clusterDescription;

    private String gameMode;

    private String pvp;

    private String maxPlayers;

    private String clusterPassword;

    private String token;

}
