package com.tugos.dst.admin.controller;


import com.tugos.dst.admin.common.ResultCodeEnum;
import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.config.I18nResourcesConfig;
import com.tugos.dst.admin.exception.ResultException;
import com.tugos.dst.admin.utils.SafeLoginCheckUtils;
import com.tugos.dst.admin.utils.URL;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qinming
 * @date 2020-05-16
 * <p> 登陆器 </p>
 */
@Controller
public class LoginController {

    /**
     * 跳转到登录页面
     */
    @GetMapping("/login")
    public String toLogin() {
        return "login";
    }

    /**
     * 实现登录
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultVO<URL> login(HttpServletRequest request,String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new ResultException(ResultCodeEnum.USER_NAME_PWD_NULL);
        }
        if (!SafeLoginCheckUtils.isAllowLogin(request)) {
            throw new ResultException(ResultCodeEnum.USER_HAS_FREEZE);
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
        SafeLoginCheckUtils.cleanErrorRecord(request);
        //登录成功，跳转至首页
        ResultVO<URL> data = ResultVO.data(new URL("/"));
        data.setMessage(I18nResourcesConfig.getMessage("tip.login.success"));
        return data;
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }

    /**
     * 权限不足页面
     */
    @GetMapping("/noAuth")
    public String noAuth() {
        return "system/main/noAuth";
    }

}
