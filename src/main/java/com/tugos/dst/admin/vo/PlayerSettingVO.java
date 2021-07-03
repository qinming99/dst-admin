package com.tugos.dst.admin.vo;

import lombok.Data;

import java.util.List;

/**
 * @author qinming
 * @date 2021-07-03 12:53:37
 * <p> 玩家管理页黑名单vo </p>
 */
@Data
public class PlayerSettingVO {

    /**
     * 管理页列表
     */
    private List<String> adminList;

    /**
     * 黑名单列表
     */
    private List<String> blackList;

}
