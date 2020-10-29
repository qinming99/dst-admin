package com.tugos.dst.admin.vo;

import lombok.Data;

/**
 * @author qinming
 * @date 2020-10-29 22:44:25
 * <p> 更新密码VO </p>
 */
@Data
public class UpdatePwdVO {

    private String oldPwd;

    private String newPwd;

    private String confirmPwd;

}
