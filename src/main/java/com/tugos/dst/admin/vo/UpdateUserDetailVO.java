package com.tugos.dst.admin.vo;

import lombok.Data;

/**
 * @author Helb
 * @description 更新用户信息
 * @date 2020/12/15 11:00
 **/
@Data
public class UpdateUserDetailVO {

    private String nickName;

    private String userName;

    private String headImg;

    private String email;

    private String phone;

}
