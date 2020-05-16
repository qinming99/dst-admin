package com.tugos.dst.admin.entity;


import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class Menu {


    private Long id;

    private Long pid;

    private String pids;

    private String title;

    private String url;

    private String perms;

    private String icon;

    private Integer type;

    private Integer sort;

    private String remark;

    private Date createDate;

    private Date updateDate;


    private Map<Long, Menu> children = new HashMap<>();


}
