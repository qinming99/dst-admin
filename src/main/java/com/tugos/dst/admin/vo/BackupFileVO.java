package com.tugos.dst.admin.vo;


import lombok.Data;

import java.util.Date;

@Data
public class BackupFileVO {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小 单位MB
     */
    private String fileSize;

    /**
     * 创建时间
     */
    private String createTime;

    private Date time;

}
