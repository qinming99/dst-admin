package com.tugos.dst.admin.entity;


import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qinming
 * @date 2020-5-16
 * <p> 菜单 </p>
 */
@Data
@Builder
public class Menu {

    private Long id;

    private String title;

    private String url;

    private String icon;

    private Integer type;

    private Integer sort;

    private Map<Long, Menu> children = new HashMap<>();

}
