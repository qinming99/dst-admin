package com.tugos.dst.admin.vo;


import lombok.Data;

import java.util.Date;
/**
 * @author qinming
 * @date 2020-05-27
 * <p> 存档信息VO </p>
 */
@Data
public class BackupFileVO {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小 byte
     */
    private Long fileSize;

    /**
     * 创建时间
     */
    private String createTime;

    private Date time;


}
