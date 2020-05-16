package com.tugos.dst.admin.entity;


import lombok.Data;

import java.util.Date;

@Data
public class User {


    private Long id;

    private String username;

    private String password;

    private String salt;

    private String nickname;

    private String picture;

    private Byte sex;

    private String phone;

    private String email;

    private Date createDate;

    private Date updateDate;

    private String remark;




}
